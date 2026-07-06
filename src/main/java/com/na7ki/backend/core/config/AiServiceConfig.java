package com.na7ki.backend.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class AiServiceConfig {

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    @Bean
    public RestClient aiServiceRestClient() {
        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5_000);
        // Inference (ResNet forward pass on CPU) is slower than a typical REST call.
        requestFactory.setReadTimeout(20_000);

        return RestClient.builder()
                .baseUrl(aiServiceUrl)
                .requestFactory(requestFactory)
                .build();
    }
}
