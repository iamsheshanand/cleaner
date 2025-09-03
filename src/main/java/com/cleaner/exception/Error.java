package com.cleaner.exception;

import org.springframework.http.HttpStatus;

public record Error(HttpStatus status, Integer code, String message) {
}
