package com.example.Config;

import org.springframework.stereotype.Component;

@Component
public class ApiServiceError implements UserFeignClient {
    @Override
    public String hello() {
        return "服务器出故障啦！！";
    }
}
