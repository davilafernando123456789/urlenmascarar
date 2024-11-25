package com.proempresa.urlenmascarar.model.repository;

import com.proempresa.urlenmascarar.model.Entity.Url;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UrlRepository extends JpaRepository<Url, Long> {
    @Query("SELECT c FROM Url c WHERE c.hash = ?1")
    Optional<Url> findByHash(String hash);

    Url findByHashAndExito(String hash, int exito);

    @Query("SELECT c FROM Url c WHERE c.longUrl = ?1")
    Optional<Url> findByLongUrl(String longUrl);
}