package com.poc.pocmastertest.config;


import java.net.http.HttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public HttpClient httpClient(){
        return HttpClient.newBuilder().build();
    }
}
