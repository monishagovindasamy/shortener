package com.url.shortener.service.impl;

import com.url.shortener.entity.UrlStoreEntity;
import com.url.shortener.exception.UrlNotFoundException;
import com.url.shortener.helper.BaseConversionHelper;
import com.url.shortener.model.UrlDetailsModel;
import com.url.shortener.repository.UrlShortenerRepository;
import com.url.shortener.service.UrlShortenerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final BaseConversionHelper baseConversionHelper;

    private final UrlShortenerRepository urlShortenerRepository;

    public UrlShortenerServiceImpl(BaseConversionHelper baseConversionHelper, UrlShortenerRepository urlShortenerRepository) {
        this.baseConversionHelper = baseConversionHelper;
        this.urlShortenerRepository = urlShortenerRepository;
    }

    @Override
    @Transactional
    public String getShortUrl(UrlDetailsModel urlDetailsModel) {
        var optionalUrlStoreEntity = urlShortenerRepository.findByUrl(urlDetailsModel.getUrl());
        if (optionalUrlStoreEntity.isEmpty()) {
            return saveAndReturnShortUrl(urlDetailsModel);
        }
        return baseConversionHelper.convertIdToShortUrl(optionalUrlStoreEntity.get().getId());
    }

    @Override
    public String getLongUrl(String shortUrl) {
        Long id = baseConversionHelper.convertShortUrlToId(shortUrl);
        Optional<UrlStoreEntity> optionalUrlStoreEntity = urlShortenerRepository.findById(id);
        if (optionalUrlStoreEntity.isEmpty()) {
            throw new UrlNotFoundException("Long url not found for the provided short url");
        }
        return optionalUrlStoreEntity.get().getUrl();
    }

    private String saveAndReturnShortUrl(UrlDetailsModel urlDetailsModel) {
        UrlStoreEntity urlStoreEntity = new UrlStoreEntity();
        urlStoreEntity.setUrl(urlDetailsModel.getUrl());
        urlStoreEntity = urlShortenerRepository.save(urlStoreEntity);
        return baseConversionHelper.convertIdToShortUrl(urlStoreEntity.getId());
    }
}
