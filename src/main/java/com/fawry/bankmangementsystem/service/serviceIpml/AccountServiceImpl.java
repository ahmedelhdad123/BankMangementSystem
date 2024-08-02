package com.fawry.bankmangementsystem.service.serviceIpml;



import com.fawry.bankmangementsystem.dto.AccountDto;
import com.fawry.bankmangementsystem.dto.UserDto;
import com.fawry.bankmangementsystem.dto.mabstrauct.AccountMapper;
import com.fawry.bankmangementsystem.dto.mabstrauct.UserMapper;
import com.fawry.bankmangementsystem.entity.Account;
import com.fawry.bankmangementsystem.entity.User;
import com.fawry.bankmangementsystem.exception.AccountException;
import com.fawry.bankmangementsystem.repository.AccountRepo;
import com.fawry.bankmangementsystem.repository.UserRepo;
import com.fawry.bankmangementsystem.service.AccountService;
import com.fawry.bankmangementsystem.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final UserMapper userMapper;
    private final UserRepo userRepo;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountDto createAccount(UserDto userDto) {
        if (accountRepo.existsByEmail(userDto.getEmail())) {
            throw new AccountException("Email already exists");
        }
        User user=userMapper.ToUser(userDto);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepo.save(user);
        Account account = accountMapper.toAccount(generateUniqueCardNumber(),Utils.generateCVV(),user);
        accountRepo.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public List<AccountDto> getMyAccounts(String email) {
        User user = CheckUser(email);
        List<Account> accounts=accountRepo.findByUserId(user.getId());
        return accounts.stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto createCartInfo(String email) {
        User user = CheckUser(email);
        Account account = accountMapper.toAccount(generateUniqueCardNumber(),Utils.generateCVV(),user);
        accountRepo.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto findAccountByCardNumber(String cardNumber) {
        Account account=accountRepo.findByCardNumber(cardNumber)
                .orElseThrow(() -> new AccountException("Account not found with CartNumber Is: " + cardNumber));
        return accountMapper.toDto(account);
    }

    private User CheckUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AccountException("Account not found With email: " + email));
        return user;
    }

    public String generateUniqueCardNumber() {
        String cardNumber = Utils.generateCardNumber();

        while (accountRepo.existsByCardNumber(cardNumber)) {
            cardNumber = Utils.generateCardNumber();
        }

        return cardNumber;
    }

}
