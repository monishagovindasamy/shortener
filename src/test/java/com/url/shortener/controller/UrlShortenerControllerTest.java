package com.url.shortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.url.shortener.entity.UrlStoreEntity;
import com.url.shortener.model.UrlDetailsModel;
import com.url.shortener.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UrlShortenerController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UrlShortenerService urlShortenerService;

    private UrlStoreEntity urlStoreEntity;

    @Test
    public void testThatRetrieveShortUrlShouldReturnValidShortUrl() throws Exception {
        UrlDetailsModel urlDetailsModel = new UrlDetailsModel();
        urlDetailsModel.setUrl("https://www.google.se/");
        when(urlShortenerService.getShortUrl(any(UrlDetailsModel.class))).thenReturn("bcd");
        mockMvc.perform(post("/shortUrl")
                .content(objectMapper.writeValueAsString(urlDetailsModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("http://localhost/bcd"));
    }

    @Test
    public void testThatRetrieveShortUrlThrowExceptionForBadUrl() throws Exception {
        UrlDetailsModel urlDetailsModel = new UrlDetailsModel();
        urlDetailsModel.setUrl("hts://www.google.se/");
        when(urlShortenerService.getShortUrl(any(UrlDetailsModel.class))).thenReturn("bcd");
        mockMvc.perform(post("/shortUrl")
                .content(objectMapper.writeValueAsString(urlDetailsModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testThathandleShortUrlRedirectShouldRedirectToValidLongUrl() throws Exception {
        when(urlShortenerService.getLongUrl(any(String.class))).thenReturn("https://www.google.se/");
        mockMvc.perform(get("/bcd"))
                .andExpect(status().is3xxRedirection());
    }

}
