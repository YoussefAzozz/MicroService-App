package com.example.apigatway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ValidateTokenFilter extends AbstractGatewayFilterFactory<ValidateTokenFilter.Config> {

    public static class Config {}

    private final WebClient.Builder webClient;

    public ValidateTokenFilter(WebClient.Builder webClient) {
        super(Config.class);
        this.webClient = webClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.build()
                .get()
                .uri("http://auth-service:4005/api/validate")
                .header("Authorization", authHeader)
                .retrieve()
                .toBodilessEntity()
                .flatMap(r -> chain.filter(exchange))
                .onErrorResume(e -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
        };
    }
}
