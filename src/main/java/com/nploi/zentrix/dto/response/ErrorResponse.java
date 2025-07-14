package com.nploi.zentrix.dto.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private long timestamp;
    private String message;
    private String path;
    private HttpStatus error;
    private int statusCode;
}
