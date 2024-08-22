package com.easygrocers.javabackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.easygrocers.javabackend.entity.UserDetails;

import org.springframework.util.AntPathMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(1)
public class SwaggerSkipFilter extends OncePerRequestFilter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Value("${api.key}")
    private String apiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        UserDetails userDetails = new UserDetails("abcdefghijk", "admin");

        if (pathMatcher.match("/swagger-ui/**", path) || pathMatcher.match("/api-docs/**", path)) {

            String randomUserId = "abcdefghijk";
            String randomUserType = "admin";
            request = new HttpServletRequestWrapper(request) {
                 private final Map<String, String> customHeaders = new HashMap<>();

                {
                    customHeaders.put("X-API-Key", apiKey);
                    customHeaders.put("X-User-Id", randomUserId);
                    customHeaders.put("X-User-Type", randomUserType);
                }
                @Override
                public String getHeader(String name) {
                    String headerValue = customHeaders.get(name);
                    if (headerValue != null) {
                        return headerValue;
                    }
                    return super.getHeader(name);
                }
          
            };
            request.setAttribute("userDetails", userDetails);
            
        }

        filterChain.doFilter(request, response);

    }
}
