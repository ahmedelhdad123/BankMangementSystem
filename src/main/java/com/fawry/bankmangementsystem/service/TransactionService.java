package com.fawry.bankmangementsystem.service;

import com.fawry.bankmangementsystem.dto.TransactionDTO;
import com.fawry.bankmangementsystem.model.transaction.deposit.DepositRequestModel;
import com.fawry.bankmangementsystem.model.transaction.withdraw.WithdrawRequestModel;

public interface TransactionService {

    TransactionDTO deposit(DepositRequestModel depositRequestModel);
    TransactionDTO withdraw(WithdrawRequestModel withdrawRequestModel);
}
