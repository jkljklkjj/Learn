package com.example.Filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Zuul配置类案例
 * 建议全文背诵
 */
@Component
public class ApiFilter implements GatewayFilterFactory<ApiFilter.Config>, Ordered {

    public static class Config {
        private  String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain)->{
            System.out.println("进入自定义过滤器");
            String token = exchange.getRequest().getQueryParams().getFirst("token");
            if(!"12345".equals(token)){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    @Override
    public Config newConfig() {
        return new Config();
    }
}
