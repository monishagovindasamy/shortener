package com.url.shortener.service;

import com.url.shortener.model.UrlDetailsModel;

public interface UrlShortenerService {

    /**
     * Return shortUrl for the provided long url
     *
     * @param urlDetailsModel
     * @return shortUrl
     */
    String getShortUrl(UrlDetailsModel urlDetailsModel);

    /**
     * Return Long url for the provided short url
     *
     * @param shortUrl
     * @return longUrl
     */
    String getLongUrl(String shortUrl);
}
