package com.fawry.bankmangementsystem.dto;

import com.fawry.bankmangementsystem.entity.Transaction;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String cardNumber;
    private String cvv;
    private Double balance;
    List<Transaction> transactions=new ArrayList<>();
}
