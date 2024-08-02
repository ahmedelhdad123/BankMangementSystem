package com.fawry.bankmangementsystem.controller;

import com.fawry.bankmangementsystem.dto.TransactionDTO;
import com.fawry.bankmangementsystem.model.DepositRequestModel;
import com.fawry.bankmangementsystem.model.WithdrawRequestModel;
import com.fawry.bankmangementsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController{
    private final TransactionService transactionService;


    @PostMapping("/deposit")
    public ResponseEntity<TransactionDTO> deposit(@RequestBody DepositRequestModel depositRequestModel){
        TransactionDTO transactionDTO = transactionService.deposit(depositRequestModel);
        return ResponseEntity.ok(transactionDTO);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(@RequestBody WithdrawRequestModel withdrawRequestModel) {
            TransactionDTO transactionDTO = transactionService.withdraw(withdrawRequestModel);
            return ResponseEntity.ok(transactionDTO);
    }
}
