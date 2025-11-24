package org.hub.gateway.config;

import java.util.Objects;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver smartUserRateLimiterKeyResolver() {
        return exchange ->
            ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth instanceof JwtAuthenticationToken)
                .map(auth -> (JwtAuthenticationToken) auth)
                .mapNotNull(jwtAuth -> {
                    String userId = jwtAuth.getToken().getSubject();
                    if (userId != null) {
                        return "USER:" + userId;
                    }
                    return null;
                })
                .defaultIfEmpty(
                    "IP:" + Objects.requireNonNull(
                        Objects.requireNonNull(exchange.getRequest().getRemoteAddress())
                            .getAddress()
                            .getHostAddress()
                    )
                );
    }
}