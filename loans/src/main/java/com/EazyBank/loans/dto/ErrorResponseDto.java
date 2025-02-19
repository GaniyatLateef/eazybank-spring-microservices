package com.EazyBank.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
    name = "ErrorResponse",
    description = " Schema to hold the error response information")

public class ErrorResponseDto {

    @Schema(description = "API Path invoked by client")
    private String apiPath;

    @Schema(description = "Error code representing the error that occurred")
    private HttpStatus errorCode;

    @Schema(description = "Error message representing the error that occurred")
    private String errorMessage;

    @Schema(description = "Timestamp when the error occurred")
    private LocalDateTime timeStamp;
}
