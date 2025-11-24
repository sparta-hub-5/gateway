package org.hub.gateway.config;

// import org.springframework.cloud.gateway.route.RouteLocator;
// import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// yml로 대체하여 주석 처리함

@Configuration
public class GatewayRoutesConfig {

    //    @Bean
    //    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
    //
    //        return builder.routes()
    //            // --- hub-service 라우팅 ---
    //            .route("hub-service-route", r -> r
    //                // 1. /hub-service/test1 같은 모든 요청을 감지
    //                .path("/hub-service/**")
    //
    //                // 2. /hub-service/test1 -> /test1 경로로 재작성
    //                .filters(f -> f.rewritePath("/hub-service/(?<segment>.*)", "/${segment}"))
    //                // 3. Eureka에 등록된 "HUB-SERVICE"로 요청 전달
    //                .uri("lb://HUB-SERVICE"))
    //
    //            .route("token-direct-route", r -> r
    //                .path("/token")
    //                .uri("lb://USER-SERVICE"))
    //
    //            .build();
    //
    //            // (필요시 'mycmd' 서킷브레이커를 여기에 적용할 수 있습니다)
    //            // .filters(f -> f
    //            //     .rewritePath("/hub-service/(?<segment>.*)", "/${segment}")
    //            //     .circuitBreaker(c -> c
    //            //         .setName("mycmd") // application.yml의 resilience4j 설정
    //            //         .setFallbackUri("forward:/fallback"))) // FallbackController
    //
    //    }
}