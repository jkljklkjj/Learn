package com.example.server2;

import com.example.Config.UserFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.Config")
@EnableHystrix
@RestController
public class Server2Application {
    private final UserFeignClient userFeignClient;

    public Server2Application(UserFeignClient userFeignClient) {
        // 注入UserFeignClient
        // 从而能使用这个控制器对象来调用Server服务的控制器方法
        this.userFeignClient = userFeignClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(Server2Application.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("/hello")
    public String home(@RequestParam String name) {
        return "hello "+name+",i am from port:" +port;
    }

    @RequestMapping("/feign-test")
    public String feignTest() {
        // 调用Server服务的控制器方法
        return userFeignClient.hello();
    }
}
