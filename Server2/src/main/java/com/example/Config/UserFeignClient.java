package com.example.Config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "Server", fallback = ApiServiceError.class)
public interface UserFeignClient {
    // 引入对应服务的控制器方法
    @RequestMapping(value = "/test")
    public String hello();
}