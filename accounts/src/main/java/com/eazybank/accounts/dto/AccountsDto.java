package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
    name = "Accounts",
    description = "Schema to hold customer and account information"
)
public class AccountsDto {

    @Schema(description = "Account number of the customer", example = "1234567890")
    @NotEmpty(message = "Account number should not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Account type of the customer", example = "savings")
    @NotEmpty(message = "Account type should not be null or empty")
    private String accountType;

    @Schema(description = "Eazy Bank branch address of the customer", example = "123 Main Street, New York")
    @NotEmpty(message = "Branch address should not be null or empty")
    private String branchAddress;
}
