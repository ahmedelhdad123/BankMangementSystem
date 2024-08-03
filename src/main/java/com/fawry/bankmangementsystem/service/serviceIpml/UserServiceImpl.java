package com.fawry.bankmangementsystem.service.serviceIpml;

import com.fawry.bankmangementsystem.dto.AccountDto;
import com.fawry.bankmangementsystem.dto.UserDto;
import com.fawry.bankmangementsystem.dto.UserProfileDto;
import com.fawry.bankmangementsystem.dto.mabstrauct.AccountMapper;
import com.fawry.bankmangementsystem.dto.mabstrauct.UserMapper;
import com.fawry.bankmangementsystem.entity.Account;
import com.fawry.bankmangementsystem.entity.User;
import com.fawry.bankmangementsystem.exception.AccountException;
import com.fawry.bankmangementsystem.repository.AccountRepo;
import com.fawry.bankmangementsystem.repository.UserRepo;
import com.fawry.bankmangementsystem.service.UserService;
import com.fawry.bankmangementsystem.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final UserMapper userMapper;
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    @Override
    public AccountDto createUser(UserDto userDto) {
        validateEmailUniqueness(userDto.getEmail());

        User user = mapUserDtoToUser(userDto);
        user.setPassword(encodePassword(user.getPassword()));
        userRepository.save(user);

        Account account = createAccountForUser(user);
        accountRepo.save(account);

        return accountMapper.toDto(account);
    }

    @Override
    public UserProfileDto getUserProfile(String email) {
        User user = findUserByEmail(email);
        return userMapper.ToUserProfileDto(user);
    }

    //////////////////////////////////////////////////////////////////////
    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AccountException("Email already exists");
        }
    }

    private User mapUserDtoToUser(UserDto userDto) {
        return userMapper.ToUser(userDto);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private Account createAccountForUser(User user) {
        String cardNumber = generateUniqueCardNumber();
        String cvv = Utils.generateCVV();
        return accountMapper.toAccount(cardNumber, cvv, user);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException("User not found"));
    }

    private String generateUniqueCardNumber() {
        String cardNumber = Utils.generateCardNumber();

        while (accountRepo.existsByCardNumber(cardNumber)) {
            cardNumber = Utils.generateCardNumber();
        }

        return cardNumber;
    }
    //////////////////////////////////////////////////////////////////////////////////
}