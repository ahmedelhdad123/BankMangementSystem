package com.fawry.bankmangementsystem.model.transaction.deposit;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequestModel {

    @NotNull(message = "Card number is required")
    private String cardNumber;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount can't be negative")
    private Double amount;
}
