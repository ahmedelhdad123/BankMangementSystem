package com.fawry.bankmangementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountProfileDto {
    private String cardNumber;
    private String cvv;
    private Double balance;
}
