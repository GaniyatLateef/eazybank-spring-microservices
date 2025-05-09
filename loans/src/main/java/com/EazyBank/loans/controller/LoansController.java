package com.EazyBank.loans.controller;

import com.EazyBank.loans.constants.LoansConstants;
import com.EazyBank.loans.dto.ErrorResponseDto;
import com.EazyBank.loans.dto.LoansContactInfoDto;
import com.EazyBank.loans.dto.LoansDto;
import com.EazyBank.loans.dto.ResponseDto;
import com.EazyBank.loans.service.LoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST API for Loans in EazyBank",
    description = "CRUD REST APIs in EazyBank to CREATE, UUPDATE, FETCH and DELETE loan details"
)
@RestController
@RequestMapping(path = "api/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class LoansController {

    private static final Logger logger = LoggerFactory.getLogger(LoansController.class);

    private final LoansService loansService;

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;

    private final LoansContactInfoDto loansContactInfoDto;

    @Operation(summary = "Create loan REST API", description = "REST API to create new EazyBank loan")
    @ApiResponses({
        @ApiResponse(responseCode = "201",description = "HTTP Status CREATED"),
        @ApiResponse(responseCode = "500", description = "HTTP Internal Server Error",
                     content = @Content( schema = @Schema(implementation = ErrorResponseDto.class)) )
    })
    @PostMapping("/create")
    //URL: http://localhost:8090/api/loans/create?mobilenumber=1234567890
    public ResponseEntity<ResponseDto> createLoan(@Valid @RequestParam
                                            @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")String mobileNumber) {
        loansService.createLoan(mobileNumber);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @Operation(
        summary = "Fetch Loan Details REST API",
        description = "REST API to fetch loan details based on a mobile number")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                     content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )})
    @GetMapping("/fetch")
    //URL: http://localhost:8090/api/loans/fetch?mobilenumber=1234567890
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("eazybank-correlation-id") String correlationId,
                                                     @Valid @RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        logger.debug("fetchLoanDetails method start");
        LoansDto loansDto = loansService.fetchLoan(mobileNumber);
        logger.debug("fetchLoanDetails method end");
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(loansDto);
    }

    @Operation(
        summary = "Update Loan Details REST API",
        description = "REST API to update loan details based on a loan number")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                     content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    //URL: http://localhost:8090/api/loans/update
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
        boolean isUpdated = loansService.updateLoan(loansDto);
        if (isUpdated) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
        summary = "Delete Loan Details REST API",
        description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                     content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/delete")
    //URL: http://localhost:8090/api/loans/delete?mobilenumber=1234567890
    public ResponseEntity<ResponseDto> deleteLoanDetails(@Valid @RequestParam
                                                         @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")String mobileNumber) {
        boolean isDeleted = loansService.deleteLoan(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
        summary = "Get Build information",
        description = "Get Build information that is deployed into cards microservice"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(buildVersion);
    }

    @Operation(
        summary = "Get Java version",
        description = "Get Java versions details that is installed into cards microservice"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
        summary = "Get Contact Info",
        description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo() {
        logger.debug("Invoked Loans contact-info API");
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(loansContactInfoDto);
    }
}
