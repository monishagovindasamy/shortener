package com.url.shortener.repository;

import com.url.shortener.entity.UrlStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlShortenerRepository extends JpaRepository<UrlStoreEntity, Long> {

    Optional<UrlStoreEntity> findByUrl(String url);
}
