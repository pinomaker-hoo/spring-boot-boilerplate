package com.pinomaker.global.interceptor;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;


@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("GET") || request.getMethod().equals("DELETE")) {
            log.info("[LOG] {} : {} {}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        }

        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            try {
                ServletInputStream inputStream = request.getInputStream();
                String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

                log.info("[LOG] {} : {} {}", request.getMethod(), request.getRequestURI(), messageBody);
            } catch (Exception e) {
            }
        }
        return true;
    }
}
