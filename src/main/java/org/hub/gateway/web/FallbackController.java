package org.hub.gateway.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/default")
    public Mono<ResponseEntity<String>> defaultFallback() {
        return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body("서비스가 일시적으로 불안정합니다. 잠시 후 다시 시도해주세요."));
    }

    @GetMapping("/user")
    public Mono<ResponseEntity<String>> userServiceFallback() {
        return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body("User-Service가 응답하지 않습니다. 잠시 후 다시 시도해주세요."));
    }

    @GetMapping("/hub")
    public Mono<ResponseEntity<String>> hubServiceFallback() {
        return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body("Hub-Service가 응답하지 않습니다. 잠시 후 다시 시도해주세요."));
    }
}