package org.hub.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, UriProperties props) {
        String httpUri = props.getHttpbin();

        return builder.routes()
            // 단순 헤더 추가 라우트
            .route("httpbin-get", r -> r
                .path("/get")
                .filters(f -> f.addRequestHeader("Hello", "World"))
                .uri(httpUri))

            // 서킷브레이커 + 폴백
            .route("cb-httpbin", r -> r
                .host("*.circuitbreaker.com")
                .filters(f -> f
                    .circuitBreaker(c -> c
                        .setName("mycmd")
                        .setFallbackUri("forward:/fallback")))
                .uri(httpUri))

            // (예시) 서비스 디스커버리 사용 시: lb:// 로 라우팅
            // .route("product-service", r -> r
            //     .path("/product/**")
            //     .filters(f -> f.rewritePath("/product/(?<segment>.*)", "/${segment}"))
            //     .uri("lb://PRODUCT-SERVICE"))

            .build();
    }
}