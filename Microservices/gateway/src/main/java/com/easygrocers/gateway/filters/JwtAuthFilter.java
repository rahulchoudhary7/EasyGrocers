package com.easygrocers.gateway.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        System.out.println("Incoming request path: " + path);

        // Skip JWT verification for public APIs
        if (path.startsWith("/api/v1/public")
                || path.startsWith("/api/v1/auth")
                || path.startsWith("/api/v1/seller/byCategory")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            System.out.println("Received token: " + token);
            System.out.println("JWT Secret" + jwtSecret);

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                if (claims.getExpiration().before(new Date())) {
                    System.out.println("Token has expired");
                    return createErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Token has expired");
                }

                String userId = claims.get("id", String.class);
                String userType = claims.get("userType", String.class);

                System.out.println("User ID - " + userId);
                System.out.println("User Type - " + userType);

                ServerHttpRequest request = exchange.getRequest().mutate()
                        .header("X-User-Id", userId)
                        .header("X-User-Roles", userType)
                        .build();

                exchange = exchange.mutate().request(request).build();

                // Continue with the filter chain - this preserves downstream error responses
                return chain.filter(exchange);

            } catch (Exception e) {
                System.err.println("JWT validation failed: " + e.getMessage());
                return createErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Invalid token");
            }
        } else {
            System.out.println("No valid Authorization header found");
        }

        return createErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    private Mono<Void> createErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().add("Content-Type", "application/json");

        // Create error response in the same format as your microservices
        String errorJson = String.format(
                "{\"status\":%d,\"message\":\"%s\",\"timestamp\":%d}",
                status.value(), message, System.currentTimeMillis()
        );

        DataBuffer buffer = response.bufferFactory().wrap(errorJson.getBytes());
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}