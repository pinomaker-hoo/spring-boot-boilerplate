package com.pinomaker.global.exception;

public class UnAuthenticationException extends RuntimeException {
    public UnAuthenticationException(final String message) {
        super(message);
    }
}
