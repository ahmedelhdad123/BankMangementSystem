package com.fawry.bankmangementsystem.service.serviceIpml;

import com.fawry.bankmangementsystem.dto.AccountDto;
import com.fawry.bankmangementsystem.dto.mabstrauct.AccountMapper;
import com.fawry.bankmangementsystem.entity.Account;
import com.fawry.bankmangementsystem.entity.User;
import com.fawry.bankmangementsystem.exception.AccountException;
import com.fawry.bankmangementsystem.repository.AccountRepo;
import com.fawry.bankmangementsystem.repository.UserRepo;
import com.fawry.bankmangementsystem.service.AccountService;
import com.fawry.bankmangementsystem.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final UserRepo userRepo;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountDto> getMyAccounts() {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        User user = findUserByEmail(email);
        List<Account> accounts = accountRepo.findByUserId(user.getId());
        return mapAccountsToDtos(accounts);
    }

    @Override
    public AccountDto createCartInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = findUserByEmail(email);
        Account account = createAccountForUser(user);
        accountRepo.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto findAccountByCardNumber() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = findUserByEmail(email);

        Account account = findAccountByCardNumberOrThrow(user.getAccounts().get(0).getCardNumber());
        return accountMapper.toDto(account);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private User findUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new AccountException("User not found with email: " + email));
    }

    private List<AccountDto> mapAccountsToDtos(List<Account> accounts) {
        return accounts.stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    private Account createAccountForUser(User user) {
        String cardNumber = generateUniqueCardNumber();
        String cvv = Utils.generateCVV();
        return accountMapper.toAccount(cardNumber, cvv, user);
    }

    private Account findAccountByCardNumberOrThrow(String cardNumber) {
        return accountRepo.findByCardNumber(cardNumber)
                .orElseThrow(() -> new AccountException("Account not found with card number: " + cardNumber));
    }

    private String generateUniqueCardNumber() {
        String cardNumber = Utils.generateCardNumber();

        while (accountRepo.existsByCardNumber(cardNumber)) {
            cardNumber = Utils.generateCardNumber();
        }

        return cardNumber;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
}