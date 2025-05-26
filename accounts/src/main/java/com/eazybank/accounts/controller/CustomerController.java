package com.eazybank.accounts.controller;

import com.eazybank.accounts.dto.CustomerDetailsDto;
import com.eazybank.accounts.dto.ErrorResponseDto;
import com.eazybank.accounts.service.CustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@Tag(
    name = "REST API for Customers in EazyBank",
    description = "EazyBank REST APIs to FETCH customer details"
)
@RestController
@RequestMapping(path="api/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
//@RequiredArgsConstructor
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomersService customerService;

    public CustomerController(CustomersService customerService) {
        this.customerService = customerService;
    }

    @Operation(
        summary = "Fetch Customer Details REST API",
        description = "REST API to fetch customer details by mobile number")
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
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("eazybank-correlation-id") String correlationId,
                                                                   @RequestParam
                                                                   @Pattern(regexp = "(^$|[0-9]{10})", message="Mobile number must be 10 digits")
                                                                   String mobileNumber){
        logger.debug("fetchCustomerDetails method start");
        CustomerDetailsDto customerDetailsDto   = customerService.fetchCustomerDetails(mobileNumber,correlationId);
        logger.debug("fetchCustomerDetails method end");
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);

    }
}
