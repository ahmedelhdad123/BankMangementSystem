package com.fawry.bankmangementsystem.service;


import com.fawry.bankmangementsystem.dto.AccountDto;
import com.fawry.bankmangementsystem.dto.UserDto;

import java.util.List;

public interface AccountService {


    List<AccountDto> getMyAccounts();
    AccountDto createCartInfo();
    AccountDto findAccountByCardNumber();
    AccountDto deleteAccountByCardNumber(String cardNumber);

}
