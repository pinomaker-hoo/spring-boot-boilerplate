package com.mars.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import com.mars.global.dto.UserDetailDto;
import com.mars.global.jwt.JwtTokenExtractor;


@Slf4j
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtTokenExtractor jwtTokenExtractor;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);
        request.setAttribute("user", userDetailDto);
        log.info("USER ID : {}", userDetailDto.toString());

        return true;
    }
}
