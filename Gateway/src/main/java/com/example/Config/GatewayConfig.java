package com.example.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Filter.ApiFilter;

@Configuration
public class GatewayConfig {
    
    @Bean
    public ApiFilter apiFilter(){
        return new ApiFilter();
    }
}
