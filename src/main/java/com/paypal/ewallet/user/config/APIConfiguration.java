package com.paypal.ewallet.user.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class APIConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){// restTemplate is a medium, similar to the template in redis, kafka, which is
        // needed to call a diff service/ API.
        return new RestTemplate();
    }


}
