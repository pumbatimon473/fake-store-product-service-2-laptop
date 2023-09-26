package com.demo.fakestoreproductservice2.config;

import com.demo.fakestoreproductservice2.FakeStoreProductService2Application;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    // Fake Store API Endpoints
    public static final String FAKE_STORE_API_HOST = "https://fakestoreapi.com";
    public static final String FAKE_STORE_PRODUCTS = FAKE_STORE_API_HOST + "/products";
    public static final String FAKE_STORE_PRODUCTS_RESOURCE = FAKE_STORE_API_HOST + "/products/{id}";
    public static final String FAKE_STORE_PRODUCTS_CATEGORIES = FAKE_STORE_API_HOST + "/products/categories";
    public static final String FAKE_STORE_PRODUCTS_BY_CATEGORY = FAKE_STORE_API_HOST + "/products/category/{categoryName}";

    // Switches
    public static final Boolean ENABLE_LOGGING = true;

    // Beans
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    /**
     * REQUIRED to support PATCH method.
     * NOTE: Standard JDK Http Lib does not support PATCH
     * Use Apache HttpComponents
     * Ref: https://stackoverflow.com/questions/29447382/resttemplate-patch-request
     */
    public RestTemplate restTemplateHttpClient(RestTemplateBuilder builder) {
        // Way 1: VERBOSE
        /*
        RestTemplate restTemplate = builder.build();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
         */
        // Way 2: COMPACT
        return builder.requestFactory(HttpComponentsClientHttpRequestFactory.class).build();
    }

    @Bean
    public Logger log() {
        return LoggerFactory.getLogger(FakeStoreProductService2Application.class);
    }
}
