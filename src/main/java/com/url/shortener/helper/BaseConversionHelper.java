package com.url.shortener.helper;

import org.springframework.stereotype.Component;

@Component
public class BaseConversionHelper {

    private static final char[] charArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    /**
     * Converts Id into short url using base62 encoding
     *
     * @param id
     * @return shortUrl
     */
    public String convertIdToShortUrl(Long id) {
        StringBuffer shortUrl = new StringBuffer();
        while (id > 0) {
            shortUrl.append(charArray[(int) (id % 62)]);
            id = id / 62;
        }
        return shortUrl.toString();
    }

    /**
     * Converts Short url in to Id using base62 decoding
     *
     * @param shortURL
     * @return id
     */
    public Long convertShortUrlToId(String shortURL) {
        Long id = 0l;
        for (int i = shortURL.length() - 1; i >= 0; i--) {
            if ('a' <= shortURL.charAt(i) &&
                    shortURL.charAt(i) <= 'z') {
                id = id * 62 + shortURL.charAt(i) - 'a';
            } else if ('A' <= shortURL.charAt(i) &&
                    shortURL.charAt(i) <= 'Z') {
                id = id * 62 + shortURL.charAt(i) - 'A' + 26;
            } else if ('0' <= shortURL.charAt(i) &&
                    shortURL.charAt(i) <= '9') {
                id = id * 62 + shortURL.charAt(i) - '0' + 52;
            }
        }
        return id;
    }
}
