package com.restaurante.Ecomerce_backend.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;
    private String details;
}
