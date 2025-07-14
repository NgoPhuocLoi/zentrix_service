package com.nploi.zentrix;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.nploi.zentrix.dto.response.ErrorResponse;
import com.nploi.zentrix.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException resourceNotFound,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                resourceNotFound.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(System.currentTimeMillis())
                .message(ex.getMessage())
                .path(request.getDescription(
                        false)) // You can modify this to include the actual path if needed
                .error(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
