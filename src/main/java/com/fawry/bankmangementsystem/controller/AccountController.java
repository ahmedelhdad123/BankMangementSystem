package com.fawry.bankmangementsystem.controller;


import com.fawry.bankmangementsystem.dto.AccountDto;
import com.fawry.bankmangementsystem.dto.UserDto;
import com.fawry.bankmangementsystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping("{email}")
    public ResponseEntity<List<AccountDto>> getAccounts(@PathVariable("email") String email) {
        List<AccountDto> accountDtoList=accountService.getMyAccounts(email);
        return ResponseEntity.ok().body(accountDtoList);
    }

    @GetMapping("findByCart/{cartNumber}")
    public ResponseEntity<AccountDto> getAccountsByCartNumber(@PathVariable("cartNumber") String cartNumber) {
        AccountDto accountDto=accountService.findAccountByCardNumber(cartNumber);
        return ResponseEntity.ok().body(accountDto);
    }

    @GetMapping("/addCartInfo/{email}")
    public ResponseEntity<AccountDto> addCartInfo(@PathVariable("email") String email) {
        AccountDto accountDto=accountService.createCartInfo(email);
        return ResponseEntity.ok().body(accountDto);
    }
}
