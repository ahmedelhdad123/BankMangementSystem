package com.fawry.bankmangementsystem.controller;

import com.fawry.bankmangementsystem.model.response.ResponseModel;
import com.fawry.bankmangementsystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/myAccount")
    public ResponseEntity<ResponseModel> getAccounts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(accountService.getMyAccounts())
                        .build()
                );
    }

    @GetMapping("/cardNumber")
    public ResponseEntity<ResponseModel> getAccountsByCartNumber() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(accountService.findAccountByCardNumber())
                        .build()
                );
    }


    @GetMapping("/addCard")
    public ResponseEntity<ResponseModel> addCartInfo() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.CREATED)
                        .success(true)
                        .data(accountService.createCartInfo())
                        .build()
                );
    }

    @DeleteMapping("/{cardNumber}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String cardNumber) {
        accountService.deleteAccountByCardNumber(cardNumber);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
