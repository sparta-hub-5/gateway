package org.hub.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // WebFlux용 Spring Security 활성화
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
            .authorizeExchange(exchanges -> exchanges
                // Eureka 대시보드나 Actuator 등은 내부에서만 접근하도록 하거나, 일단은 열어둡니다.
                // (실제 프로덕션에서는 이 부분도 보호해야 합니다.)
                .pathMatchers("/eureka/**", "/actuator/**").permitAll()

                // '/hub-service/' 또는 '/user-service/'로 시작하는 모든 MSA 라우팅 요청은
                // 반드시 인증(JWT 토큰)을 거쳐야 함
                .pathMatchers("/hub-service/**", "/user-service/**").authenticated()

                // 그 외 모든 요청도 일단 인증을 요구 (정책에 따라 변경)
                .anyExchange().authenticated()
            )
            // OAuth2 리소스 서버 설정을 활성화하고, JWT 검증을 기본값으로 사용
            // (이 부분이 spring.security.oauth2.resourceserver.jwt.issuer-uri 설정과 연동됨)
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        // CSRF는 Gateway에서 일반적으로 비활성화합니다. (토큰 기반 인증)
        http.csrf(CsrfSpec::disable);

        return http.build();
    }
}