package com.url.shortener.model;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UrlDetailsModel {

    @URL(message = "Invalid url")
    private String url;
}
