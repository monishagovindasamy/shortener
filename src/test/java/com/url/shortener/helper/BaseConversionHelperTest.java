package com.url.shortener.helper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BaseConversionHelperTest {

    @InjectMocks
    private BaseConversionHelper baseConversionHelper;

    @Test
    public void testThatConvertIdToShortUrlShouldReturnValidShortUrl() {
        assertEquals(baseConversionHelper.convertIdToShortUrl(1l), "b");
    }

    @Test
    public void testThatConvertShortUrlToIdShouldReturnValidId() {
        assertEquals(baseConversionHelper.convertShortUrlToId("b"), 1l);
    }
}
