package com.eazybank.accounts.controller;

import com.eazybank.accounts.constants.AccountsConstants;
import com.eazybank.accounts.dto.AccountsContactInfoDto;
import com.eazybank.accounts.dto.CustomerDto;
import com.eazybank.accounts.dto.ErrorResponseDto;
import com.eazybank.accounts.dto.ResponseDto;
import com.eazybank.accounts.service.AccountsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
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

import java.util.concurrent.TimeoutException;

@Tag(
    name = "CRUD REST APIs for Accounts in EazyBank",
    description = "CRUD REST APIs for Accounts in EazyBank to CREATE, FETCH, UPDATE and DELETE account details"
)
@RestController
@RequestMapping(path="api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AccountsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    private final AccountsService accountsService;

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;

    private final AccountsContactInfoDto accountsContactInfoDto;


    @Operation(
        summary = "Create Account REST API",
        description = "REST API to create a new customer and account in EazyBank")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status INTERNAL_SERVER_ERROR",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @PostMapping("/create")
    //URL: http://localhost:8080/api/create
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
        summary = "Fetch Account Details REST API",
        description = "REST API to fetch customer and account details by mobile number")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status INTERNAL_SERVER_ERROR",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @GetMapping("/fetch")
    //URL: http://localhost:8080/api/fetch
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                        @Pattern(regexp = "(^$|[0-9]{10})", message="Mobile number must be 10 digits")
                                                               String mobileNumber) {
        CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(customerDto);
    }

    @Operation(
        summary = "update Account Details REST API",
        description = "REST API to update customer and account details by account number")
    @ApiResponses({
           @ApiResponse(
              responseCode = "200",
              description = "HTTP Status OK"),
           @ApiResponse(
               responseCode = "417",
               description = "HTTP Status EXPECTATION_FAILED"
           ),
           @ApiResponse(
               responseCode = "500",
               description = "HTTP Status INTERNAL_SERVER_ERROR",
               content = @Content(
                   schema = @Schema(implementation = ErrorResponseDto.class)
               ))
        }
    )
    @PutMapping("/update")

    //URL: http://localhost:8080/api/accounts/update
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
        summary = "Delete Account and Customer Details REST API",
        description = "REST API to delete customer and account details by mobile number")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"),
        @ApiResponse(
            responseCode = "417",
            description = "HTTP Status EXPECTATION_FAILED"),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status INTERNAL_SERVER_ERROR",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @DeleteMapping("/delete")
    //URL: http://localhost:8080/api/accounts/delete
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                        @Pattern(regexp = "(^$|[0-9]{10})", message="Mobile number must be 10 digits")
                                                                String mobileNumber) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
        summary = "Get Build information",
        description = "Get build information that is deployed into accounts microservice")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status INTERNAL_SERVER_ERROR",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @Retry(name="getBuildInfo",fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String>geBuildInfo(){
        logger.debug("getbuildInfo() method Invoked");
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(buildVersion);
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
        logger.debug("getBuildInfoFallback() method Invoked");
    return ResponseEntity
        .status(HttpStatus.OK)
        .body("0.9");
    }

    @Operation(
        summary = "Get Java version",
        description = "Get Java version installed into accounts microservice")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status INTERNAL_SERVER_ERROR",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @RateLimiter(name="getJavaVersion",fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    public ResponseEntity<String>getJavaVersion(){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(environment.getProperty("JAVA_HOME"));
    }
    public ResponseEntity<String>getJavaVersionFallback(Throwable throwable){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Java 21");
    }

    @Operation(
        summary = "Get contact info",
        description = "Contact information details that can be reached out to in case of any issues")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status INTERNAL_SERVER_ERROR",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accountsContactInfoDto);
    }

}
