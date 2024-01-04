package com.mars.global.config;

import com.mars.global.interceptor.JwtInterceptor;
import com.mars.global.interceptor.LoggingInterceptor;
import com.mars.global.jwt.JwtTokenExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 * Interceptor Configuration
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableSpringDataWebSupport
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    private final JwtTokenExtractor jwtTokenExtractor;

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/resources/",
            "classpath:/static/", ""};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // ** Logging Interceptor
        registry.addInterceptor(new LoggingInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/health")
                .order(0);

        // ** Jwt Interceptor
        registry.addInterceptor(new JwtInterceptor(jwtTokenExtractor))
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/v1/auth/**")
                .excludePathPatterns("/api/health")
                .order(1);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setMaxPageSize(100000);
        argumentResolvers.add(resolver);
    }
}
