package com.fawry.bankmangementsystem.service;


import com.fawry.bankmangementsystem.dto.AccountDto;
import com.fawry.bankmangementsystem.dto.UserDto;

import java.util.List;

public interface AccountService {


    List<AccountDto> getMyAccounts(String email);
    AccountDto createCartInfo(String email);
    AccountDto findAccountByCardNumber(String cardNumber);

}
