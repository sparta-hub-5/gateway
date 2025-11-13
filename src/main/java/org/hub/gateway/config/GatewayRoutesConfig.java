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
            .route("hub-service-route", r -> r
                .path("/hub-service/**") // 1. "/hub-service/"로 시작하는 모든 요청
                // 2. "/hub-service/test1" -> "/test1"로 경로 재작성
                .filters(f -> f.rewritePath("/hub-service/(?<segment>.*)", "/${segment}"))
                // 3. Eureka에 등록된 "HUB-SERVICE"로 로드 밸런싱
                .uri("lb://HUB-SERVICE"))
            // ^^^--- 이 라우트를 추가해야 합니다 ---^^^

            .build();
    }
}