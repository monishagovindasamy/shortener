package com.url.shortener.controller;

import com.url.shortener.model.UrlDetailsModel;
import com.url.shortener.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Validated
@RestController
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * Convert provided long url into short url
     *
     * @param urlDetailsModel
     * @param httpServletRequest
     * @return ResponseEntity with short url
     */
    @PostMapping("shortUrl")
    public ResponseEntity retrieveShortUrl(@Valid @RequestBody UrlDetailsModel urlDetailsModel, HttpServletRequest httpServletRequest) {
        String shortUrl = urlShortenerService.getShortUrl(urlDetailsModel);
        String url = getHostDetails(httpServletRequest) + "/" + shortUrl;
        return ResponseEntity.ok(url);
    }

    /**
     * Redirects the short url to long url stored in db
     *
     * @param shortUrl
     * @param httpResponse
     */
    @GetMapping("{shortUrl}")
    public void handleShortUrlRedirect(@PathVariable("shortUrl") String shortUrl, HttpServletResponse httpResponse) {
        String url = urlShortenerService.getLongUrl(shortUrl);
        httpResponse.addHeader("Location", url);
        httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    }

    private String getHostDetails(HttpServletRequest httpServletRequest) {
        String protocol = httpServletRequest.getScheme();
        String host = httpServletRequest.getServerName();
        int port = httpServletRequest.getServerPort();
        if (port == -1 || port == 80) {
            return String.format("%s://%s", protocol, host);
        }
        return String.format("%s://%s:%d", protocol, host, port);
    }

}
