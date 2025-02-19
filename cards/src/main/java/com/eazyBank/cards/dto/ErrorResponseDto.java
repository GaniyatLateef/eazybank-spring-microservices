package com.eazyBank.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(name = "ErrorResponseDto", description = "Schema to hold error response information")
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(description = "API path invoked by the client")
    private String apiPath;

    @Schema(description = "Error code representing the error that occurred")
    private HttpStatus errorCode;

    @Schema(description = "Error message representing the error that occurred")
    private String errorMessage;

    @Schema(description = "Timestamp when the error occurred")
    private LocalDateTime timeStamp;
}
