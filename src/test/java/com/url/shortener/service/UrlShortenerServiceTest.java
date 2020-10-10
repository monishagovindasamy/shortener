package com.url.shortener.service;

import com.url.shortener.entity.UrlStoreEntity;
import com.url.shortener.exception.UrlNotFoundException;
import com.url.shortener.helper.BaseConversionHelper;
import com.url.shortener.model.UrlDetailsModel;
import com.url.shortener.repository.UrlShortenerRepository;
import com.url.shortener.service.impl.UrlShortenerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceTest {

    private static final String URL = "https://www.google.se/";

    @InjectMocks
    private UrlShortenerServiceImpl urlShortenerServiceImpl;

    @Mock
    private UrlShortenerRepository urlShortenerRepository;

    @Mock
    private BaseConversionHelper baseConversionHelper;

    private UrlDetailsModel urlDetailsModel;

    private UrlStoreEntity urlStoreEntity;

    @BeforeEach
    public void setup() {
        urlDetailsModel = new UrlDetailsModel();
        urlStoreEntity = new UrlStoreEntity();
        urlDetailsModel.setUrl(URL);
        urlStoreEntity.setUrl(URL);
        urlStoreEntity.setId(0l);

    }

    @Test
    public void testThatGetShortUrlShouldReturnValidShortUrlWhenFound() {
        when(urlShortenerRepository.findByUrl(urlDetailsModel.getUrl())).thenReturn(Optional.ofNullable(urlStoreEntity));
        when(baseConversionHelper.convertIdToShortUrl(0l)).thenReturn("a");

        String shortUrl = urlShortenerServiceImpl.getShortUrl(urlDetailsModel);

        assertEquals(shortUrl, "a");
    }

    @Test
    public void testThatGetShortUrlShouldReturnValidShortUrlWhenNotFound() {
        when(urlShortenerRepository.findByUrl(urlDetailsModel.getUrl())).thenReturn(Optional.empty());
        when(urlShortenerRepository.save(any(UrlStoreEntity.class))).thenReturn(urlStoreEntity);
        when(baseConversionHelper.convertIdToShortUrl(anyLong())).thenReturn("a");

        String shortUrl = urlShortenerServiceImpl.getShortUrl(urlDetailsModel);

        verify(urlShortenerRepository, times(1)).save(urlStoreEntity);
        assertEquals(shortUrl, "a");
    }

    @Test
    public void testThatGetLongUrlShouldReturnValidLongUrlWhenFound() {
        when(baseConversionHelper.convertShortUrlToId("a")).thenReturn(0l);
        when(urlShortenerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(urlStoreEntity));

        assertEquals(urlShortenerServiceImpl.getLongUrl("a"), URL);
    }

    @Test
    public void testThatGetLongUrlShouldThrowExceptionWhenShortUrlNotFound() {
        when(urlShortenerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(UrlNotFoundException.class, () -> {
            urlShortenerServiceImpl.getLongUrl("a");
        });
    }
}
