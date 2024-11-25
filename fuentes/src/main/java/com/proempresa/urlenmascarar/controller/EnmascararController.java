package com.proempresa.urlenmascarar.controller;

import com.proempresa.urlenmascarar.model.request.UrlRequest;
import com.proempresa.urlenmascarar.model.response.NotificarUrlResponse;
import com.proempresa.urlenmascarar.model.response.UrlResponse;
import com.proempresa.urlenmascarar.service.impl.UrlService;
import com.proempresa.urlenmascarar.util.LoggerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/campania")
@RequiredArgsConstructor
public class EnmascararController {
    private final UrlService urlService;

    @PostMapping("/url/acortar")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest request) {
        LoggerUtil.printInfo("Entrando al controlador de acortar. ");
        UrlResponse urlResponse = urlService.acortarUrl(request);
        LoggerUtil.printInfo("Url acortada: ", urlResponse.getUrl());
        return ResponseEntity.ok(urlResponse);
    }

    @GetMapping("/edn/notificar/{shortUrl}")
    public ResponseEntity<NotificarUrlResponse> executeUrl(@PathVariable String shortUrl) {
        LoggerUtil.printInfo("Entrando al controlador de acortar. ");
        NotificarUrlResponse notificarUrlResponse = urlService.obtenerUrl(shortUrl);
        LoggerUtil.printInfo("Url acortada: ", notificarUrlResponse.getMensaje());
        return ResponseEntity.ok(notificarUrlResponse);
    }
}
