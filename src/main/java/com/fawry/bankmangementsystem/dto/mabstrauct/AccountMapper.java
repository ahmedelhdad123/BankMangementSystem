package com.fawry.bankmangementsystem.dto.mabstrauct;

import com.fawry.bankmangementsystem.dto.AccountDto;
import com.fawry.bankmangementsystem.dto.AccountProfileDto;
import com.fawry.bankmangementsystem.entity.Account;
import com.fawry.bankmangementsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account account);

//    Account toAccount(AccountDto accountDto);

    @Mapping(source = "user",target = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance",constant = "0.0")
    Account toAccount(String cardNumber, String cvv, User user);


    AccountProfileDto toAccountProfileDto(Account account);

}
