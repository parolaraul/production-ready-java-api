package com.parolaraul.recipeapi.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class HttpExceptionFactory {
    public static ResponseEntity<HttpErrorResponse> serverError(String error, String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpErrorResponse(error, message));
    }

    public static ResponseEntity<HttpErrorResponse> notFound(String error, String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpErrorResponse(error, message));
    }

    public static ResponseEntity<HttpErrorResponse> badRequest(String error, String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpErrorResponse(error, message));
    }

    public static ResponseEntity<HttpErrorResponse> unauthorized(String error, String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HttpErrorResponse(error, message));
    }
}
