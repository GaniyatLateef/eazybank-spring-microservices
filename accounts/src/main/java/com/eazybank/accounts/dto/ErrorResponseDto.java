package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
    name = "ErrorResponse",
    description = "Schema to hold error response information")
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(description = "API path invoked by client")
    private String apiPath;

    @Schema(description = "Error code returned by the API")
    private HttpStatus errorCode;

    @Schema(description = "Error message returned by the API")
    private String errorMessage;

    @Schema(description = "Timestamp when the error occurred")
    private LocalDateTime timeStamp;
}
