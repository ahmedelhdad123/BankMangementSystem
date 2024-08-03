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
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;


    @Override
    public AccountDto createUser(UserDto userDto) {
        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new AccountException("Email already exists");
        }
        User user=userMapper.ToUser(userDto);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepo.save(user);
        Account account = accountMapper.toAccount(generateUniqueCardNumber(), Utils.generateCVV(),user);
        accountRepo.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public UserProfileDto getUserProfile(String email) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException("User not found"));

        return userMapper.ToUserProfileDto(user);
    }

    public String generateUniqueCardNumber() {
        String cardNumber = Utils.generateCardNumber();

        while (accountRepo.existsByCardNumber(cardNumber)) {
            cardNumber = Utils.generateCardNumber();
        }

        return cardNumber;
    }

}
