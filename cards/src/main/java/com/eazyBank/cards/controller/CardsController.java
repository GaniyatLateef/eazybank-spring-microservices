package com.eazyBank.cards.controller;

import com.eazyBank.cards.constants.CardsConstant;
import com.eazyBank.cards.dto.CardsContactInfoDto;
import com.eazyBank.cards.dto.CardsDto;
import com.eazyBank.cards.dto.ErrorResponseDto;
import com.eazyBank.cards.dto.ResponseDto;
import com.eazyBank.cards.service.CardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = " CRUD REST API for Cards in EazyBank",
    description = "CRUD REST APIs in EazyBank to CREATE, FETCH, UPDATE and DELETE Card details" )
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "api/cards", produces = {MediaType.APPLICATION_JSON_VALUE})

public class CardsController {

    private final CardsService cardsService;

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;

    private final CardsContactInfoDto cardsContactInfoDto;


    @Operation(
        summary = "Create a new card REST API for a given mobile number",
        description = "REST API to Create a new card for a given mobile number")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "HTTP status CREATED"),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP status Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/create")
    //URL: http://localhost:9000/api/cards/create?mobileNumber=1234567890
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam
                                                  @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number must be 10 digits long")
                                                  String mobileNumber) {
        cardsService.createCard(mobileNumber);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(CardsConstant.STATUS_201, CardsConstant.MESSAGE_201));
    }

    @Operation(
        summary = "Fetch card details REST API for a given mobile number",
        description = "REST API to fetch card details for a given mobile number"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP status OK"),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP status Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetch")
    //URL: http://localhost:9000/api/cards/fetch?mobileNumber=1234567890
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam
                                                     @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number must be 10 digits long") String mobileNumber) {
        CardsDto cardsDto = cardsService.fetchCard(mobileNumber);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(cardsDto);
    }

    @Operation(
        summary = "Update card details REST API",
        description = "REST API to update card details for a given card number"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP status OK"),
        @ApiResponse(
            responseCode = "417",
            description = "HTTP status Expectation Failed",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    //URL: http://localhost:9000/api/cards/update
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = cardsService.updateCard(cardsDto);
        if (isUpdated) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstant.STATUS_200, CardsConstant.MESSAGE_200));
        } else {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstant.STATUS_417, CardsConstant.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
        summary = "Delete card details REST API",
        description = "REST API to delete card details for a given mobile number"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP status OK"),
        @ApiResponse(
            responseCode = "417",
            description = "HTTP status Expectation Failed"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP status Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    @DeleteMapping("/delete")
    //URL: http://localhost:9000/api/cards/delete?mobileNumber=1234567890
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam
                                                         @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number must be 10 digits long") String mobileNumber) {
        boolean isDeleted = cardsService.deleteCard(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstant.STATUS_200, CardsConstant.MESSAGE_200));
        } else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(CardsConstant.STATUS_417, CardsConstant.MESSAGE_417_DELETE));
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
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(cardsContactInfoDto);
    }

    }
