package com.example.Get_Employee_details_Reactive.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
   
    @Value("${baseUrl}")
    private String url;

    @Bean
    WebClient webclient() {
        return WebClient.builder()
                .baseUrl(url)
                .build();
    } 
}
