package com.fawry.bankmangementsystem.service.serviceIpml;

import com.fawry.bankmangementsystem.dto.TransactionDTO;
import com.fawry.bankmangementsystem.dto.mabstrauct.TransactionMapper;
import com.fawry.bankmangementsystem.entity.Account;
import com.fawry.bankmangementsystem.entity.Transaction;
import com.fawry.bankmangementsystem.entity.enumrole.TransactionType;
import com.fawry.bankmangementsystem.exception.AccountException;
import com.fawry.bankmangementsystem.exception.OperationException;
import com.fawry.bankmangementsystem.model.transaction.deposit.DepositRequestModel;
import com.fawry.bankmangementsystem.model.transaction.withdraw.WithdrawRequestModel;
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
        Account account = findAccountByCardNumber(depositRequestModel.getCardNumber());
        validateDepositAmount(depositRequestModel.getAmount());

        account.setBalance(account.getBalance() + depositRequestModel.getAmount());
        accountRepo.save(account);

        Transaction transaction = createTransaction(account, depositRequestModel.getAmount(), TransactionType.DEPOSIT);
        transactionRepo.save(transaction);

        return transactionMapper.toDTO(transaction);
    }

    @Override
    public TransactionDTO withdraw(WithdrawRequestModel withdrawRequestModel) {
        Account account = findAccountByCardNumber(withdrawRequestModel.getCardNumber());
        validateWithdrawRequest(account, withdrawRequestModel);

        account.setBalance(account.getBalance() - withdrawRequestModel.getAmount());
        accountRepo.save(account);

        Transaction transaction = createTransaction(account, withdrawRequestModel.getAmount(), TransactionType.WITHDRAW);
        transactionRepo.save(transaction);

        return transactionMapper.toDTO(transaction);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private Account findAccountByCardNumber(String cardNumber) {
        return accountRepo.findByCardNumber(cardNumber)
                .orElseThrow(() -> new AccountException("Account not found with card number: " + cardNumber));
    }

    private void validateDepositAmount(double amount) {
        if (amount <= 0) {
            throw new OperationException("Deposit amount cannot be less than or equal to 0");
        }
    }

    private void validateWithdrawRequest(Account account, WithdrawRequestModel withdrawRequestModel) {
        if (!account.getCvv().equals(withdrawRequestModel.getCvv())) {
            throw new OperationException("This operation is not allowed");
        }
        if (withdrawRequestModel.getAmount() <= 0) {
            throw new OperationException("Withdraw amount cannot be less than or equal to 0");
        }
        if (account.getBalance() < withdrawRequestModel.getAmount()) {
            throw new OperationException("Insufficient balance for this operation");
        }
    }

    private Transaction createTransaction(Account account, double amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setNotes(type + " amount is: " + amount + " The total balance is: " + account.getBalance());
        transaction.setAccount(account);
        return transaction;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
}