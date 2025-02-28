package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(name = "Loans", description = " Schema to hold Loan details")
@Data
public class LoansDto {

    @NotEmpty(message = "Mobile Number can not be null or empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number must be 10 digits")
    @Schema(description = "Mobile Number of the customer", example = "1234567890")
    private String mobileNumber;

    @NotEmpty(message = "Loan Number can not be null or empty")
    @Pattern(regexp = "^[0-9]{12}$", message = "Loan Number must be 12 digits")
    @Schema(description = "Loan Number of the customer", example = "1234567890")
    private String loanNumber;

    @NotEmpty(message = "Loan Type can not be null or empty")
    @Schema(description = "Type of the loan", example = "Home Loan")
    private String loanType;

    @Positive(message = "Total loan amount should be greater than 0")
    @Schema(description = "Total loan amount", example = "100000")
    private int totalLoan;

    @PositiveOrZero(message = "Total loan amount paid shold be greater than or equal to 0")
    @Schema(description = "Total loan amount paid", example = "50000")
    private int amountPaid;

    @PositiveOrZero(message = "Total Outstanding amount should be greater than or equal to 0")
    @Schema(description = "Total outstanding amount against a loan", example = "40000")
    private int outstandingAmount;
}
