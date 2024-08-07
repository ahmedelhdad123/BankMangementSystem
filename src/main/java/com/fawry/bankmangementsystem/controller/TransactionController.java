package com.fawry.bankmangementsystem.controller;

import com.fawry.bankmangementsystem.dto.TransactionDTO;
import com.fawry.bankmangementsystem.model.DepositRequestModel;
import com.fawry.bankmangementsystem.model.ResponseModel;
import com.fawry.bankmangementsystem.model.WithdrawRequestModel;
import com.fawry.bankmangementsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseModel> deposit(@RequestBody DepositRequestModel depositRequestModel){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(transactionService.deposit(depositRequestModel))
                        .build()
                );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ResponseModel> withdraw(@RequestBody WithdrawRequestModel withdrawRequestModel) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(transactionService.withdraw(withdrawRequestModel))
                        .build()
                );
    }
}
