package com.pinomaker.global.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class CommonResponse {
    public static ResponseEntity<Object> createResponseMessage(final int statusCode, final String message) {
        return ApiResponse.builder().status(statusCode).message(message).buildObject();
    }

    public static ResponseEntity<Object> createResponse(final int statusCode, final String message, Object data) {
        return ApiResponse.builder().status(statusCode).message(message).data(data).buildObject();
    }

    public static ResponseEntity<Object> createResponseWithPagination(final int statusCode, final String message, Object data, Pagination pagination) {
        return ApiResponse.builder().status(statusCode).message(message).data(data).pagination(pagination).buildObject();
    }
}
