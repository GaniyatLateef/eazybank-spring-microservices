package com.eazyBank.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(name = "Cards", description = "Cards Dto")
@Data
public class CardsDto {

    @Schema(description = "Mobile Number", example = "1234567890")
    @NotEmpty(message = "Mobile Number can not be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number must be 10 digits long")
    private String mobileNumber;

    @Schema(description = "Card Number", example = "567890123456")
    @NotEmpty(message = "Card Number can not be null or empty")
    @Pattern(regexp = "^$|[0-9]{12}", message = "Card Number must be 12 digits long")
    private String cardNumber;

    @NotEmpty(message = "Card Type can not be null or empty")
    @Schema(description = "Type of the card", example = "Credit Card")
    private String cardType;

    @Positive(message = "Total card limit should be greater than 0")
    @Schema(description = "Total amount limit available against a card", example = "10000")
    private int totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater than 0")
    @Schema(description = "Total amount used by a customer", example = "5000")
    private int amountUsed;

    @PositiveOrZero(message = "Available amount should be equal or greater than 0")
    @Schema(description = "Total amount available against a card", example = "5000")
    private int availableAmount;
}
