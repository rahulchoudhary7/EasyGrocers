package com.easygrocers.javabackend.config;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.easygrocers.javabackend.entity.UserDetails;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(4)
public class UserInfoFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String userId = request.getHeader("X-User-Id");
        String userType = request.getHeader("X-User-Type");

        if (userId == null || userType == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing user information");
            return;
        }

        UserDetails userDetails = new UserDetails(userId, userType);
        request.setAttribute("userDetails", userDetails);

        chain.doFilter(request, response);
    }
}
