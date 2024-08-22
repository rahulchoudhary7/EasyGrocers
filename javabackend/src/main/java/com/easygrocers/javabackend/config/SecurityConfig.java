package com.easygrocers.javabackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SwaggerSkipFilter swaggerSkipFilter;

    @Autowired
    private ApiKeyFilter apiKeyFilter;

    @Autowired
    private RateLimitFilter rateLimitFilter;

    @Autowired
    private UserInfoFilter userInfoFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll())
                        .addFilterBefore(swaggerSkipFilter, UsernamePasswordAuthenticationFilter.class) 
                        .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)     
                        .addFilterAfter(rateLimitFilter, ApiKeyFilter.class)                          
                        .addFilterAfter(userInfoFilter, RateLimitFilter.class)                    
                        .build();
    }
}