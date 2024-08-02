package com.fawry.bankmangementsystem.service.serviceIpml;


import com.fawry.bankmangementsystem.dto.TransactionDTO;
import com.fawry.bankmangementsystem.dto.mabstrauct.TransactionMapper;
import com.fawry.bankmangementsystem.entity.Account;
import com.fawry.bankmangementsystem.entity.Transaction;
import com.fawry.bankmangementsystem.entity.enumrole.TransactionType;
import com.fawry.bankmangementsystem.exception.AccountException;
import com.fawry.bankmangementsystem.exception.OperationException;
import com.fawry.bankmangementsystem.model.DepositRequestModel;
import com.fawry.bankmangementsystem.model.WithdrawRequestModel;
import com.fawry.bankmangementsystem.repository.AccountRepo;
import com.fawry.bankmangementsystem.repository.TransactionRepo;
import com.fawry.bankmangementsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final TransactionMapper transactionMapper;
    private final AccountRepo accountRepo;

    @Override
    public TransactionDTO deposit(DepositRequestModel depositRequestModel) {

        Account account= accountRepo.findByCardNumber(depositRequestModel.getCardNumber())
                .orElseThrow(() -> new AccountException("Account not found With Card Number: " + depositRequestModel.getCardNumber()));

        if (depositRequestModel.getAmount()<=0){
            throw new OperationException("Deposit Amount cannot be less than or equal to 0");
        }

        account.setBalance(account.getBalance() + depositRequestModel.getAmount());
        accountRepo.save(account);

        Transaction transaction = new Transaction();
        transaction.setId(transaction.getId());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAmount(depositRequestModel.getAmount());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setNotes("Deposit amount is: " + depositRequestModel.getAmount() + " The Total Balance is: " + account.getBalance());
        transaction.setAccount(account);
//        transaction.setAccountId(account.getId());
        transactionRepo.save(transaction);

        return transactionMapper.toDTO(transaction);
    }

    public TransactionDTO withdraw(WithdrawRequestModel withdrawRequestModel) {

        Account account= accountRepo.findByCardNumber(withdrawRequestModel.getCardNumber())
                .orElseThrow(() -> new AccountException("Account not found With Card Number: " + withdrawRequestModel.getCardNumber()));
        if (!account.getCvv().equals(withdrawRequestModel.getCvv())){
            throw new OperationException("This Operation is not allowed");
        }
        if (withdrawRequestModel.getAmount()<=0){
            throw new OperationException("Withdraw Amount cannot be less than or equal to 0");
        }
         if (account.getBalance()<withdrawRequestModel.getAmount()){
             throw new OperationException("This Operation is not allowed");
         }


        account.setBalance(account.getBalance() - withdrawRequestModel.getAmount());
        accountRepo.save(account);
        Transaction transaction = new Transaction();
        transaction.setId(transaction.getId());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAmount(withdrawRequestModel.getAmount());
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setNotes("Withdraw amount is: " + withdrawRequestModel.getAmount() + " The Total Balance is: " + account.getBalance());
//        transaction.setAccountId(account.getId());
        transaction.setAccount(account);
        transactionRepo.save(transaction);
        return transactionMapper.toDTO(transaction);
    }


}
