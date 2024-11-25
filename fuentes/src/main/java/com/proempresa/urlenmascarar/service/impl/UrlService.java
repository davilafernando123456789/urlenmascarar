package com.proempresa.urlenmascarar.service.impl;

import com.proempresa.urlenmascarar.model.Entity.Url;
import com.proempresa.urlenmascarar.model.repository.UrlRepository;
import com.proempresa.urlenmascarar.model.repository.impl.UrlRepositoryImpl;
import com.proempresa.urlenmascarar.model.request.UrlRequest;
import com.proempresa.urlenmascarar.model.response.NotificarUrlResponse;
import com.proempresa.urlenmascarar.model.response.UrlResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import proempresa.apiutil.exception.BadRequestException;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlRepositoryImpl urlrepositoryimpl;
    private final RestTemplate restTemplate;

    @Autowired
    public UrlService(UrlRepository urlRepository, RestTemplate restTemplate, UrlRepositoryImpl urlrepositoryimpl) {
        this.urlRepository = urlRepository;
        this.restTemplate = restTemplate;
        this.urlrepositoryimpl = urlrepositoryimpl;
    }
    public NotificarUrlResponse obtenerUrl(String hash) {
        Url urlRequest = urlRepository.findByHashAndExito(hash, 0);
        if (urlRequest != null) {
            if (urlRequest.getExpiresDate().after(new Date())) {
                NotificarUrlResponse notificarUrlResponse = ExecuteUrl(urlRequest);
                int count = urlrepositoryimpl.actualizarRegistro(urlRequest);
                if(count == 0){
                    throw new BadRequestException("Error al actualizar el estado de la url. ");
                }
                return notificarUrlResponse;
            } else {
                throw new BadRequestException("Url expirada.");
            }
        } else {
            throw new BadRequestException("Notificación ya enviada. ");
        }
    }

    public NotificarUrlResponse ExecuteUrl(Url url) {
        NotificarUrlResponse notificarUrlResponse = new NotificarUrlResponse();
        String urlCompleta = url.getLongUrl();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(new URI(urlCompleta), String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                notificarUrlResponse.setMensaje("Notificación exitosa a: " + url);
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new BadRequestException("Registro ya existente. " + url);
            }
            else {
                throw new BadRequestException("Error al ejecutar la URL: " + url + ". Detalle del error: " + e.getMessage());
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return notificarUrlResponse;
    }
    @Transactional
    public UrlResponse acortarUrl(UrlRequest urlRequest) {
        UrlResponse urlResponse = new UrlResponse();
        try {
            String hash = generateHash(urlRequest.getUrl());

            // Filtrar el hash para que solo contenga letras y números
            String filteredHash = hash.replaceAll("[^a-zA-Z0-9]", "");
            String shortUrl = filteredHash.substring(0, Math.min(filteredHash.length(), 8));

            // Verificar si ya existe un registro con la misma URL larga
            Optional<Url> existingUrl = urlRepository.findByLongUrl(urlRequest.getUrl());
            if (existingUrl.isPresent()) {
                urlResponse.setUrl(shortUrl);
                return urlResponse;
            }

            // Verificar si ya existe un registro con el mismo hash
            Optional<Url> urlId = urlRepository.findByHash(shortUrl);
            if (urlId.isPresent()) {
                urlResponse.setUrl(shortUrl);
            } else {
                Url url = new Url();
                url.setLongUrl(urlRequest.getUrl());
                url.setHash(shortUrl);
                url.setExito(0);
                url.setCreatedDate(new Date());
                urlRepository.save(url);
                urlResponse.setUrl(shortUrl);
            }
            return urlResponse;
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException("Error al acortar url.");
        }
    }

    private String generateHash(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }
}

