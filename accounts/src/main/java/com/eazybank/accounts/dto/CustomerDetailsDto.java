package com.eazybank.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
    name = "CustomerDetails",
    description = "Schema to hold Customer,Account,Cards and Loans information")
public class CustomerDetailsDto {

    @Schema(description = "Name of the customer", example = "John Doe")
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @Schema(description = "Email address of the customer", example = "tutor@eazybytes.com")
    @NotEmpty(message = "Email address should not be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(description = "Mobile number of the customer", example = "0456789012")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Account details of the customer")
    private AccountsDto accountsDto;

    @Schema(description = "Loan details of the customer")
    private LoansDto loansDto;

    @Schema(description = "Cards details of the customer")
    private CardsDto cardsDto;
}
