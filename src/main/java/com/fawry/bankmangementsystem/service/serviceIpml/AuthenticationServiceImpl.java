package com.fawry.bankmangementsystem.service.serviceIpml;

import com.fawry.bankmangementsystem.dto.mabstrauct.AccountMapper;
import com.fawry.bankmangementsystem.dto.mabstrauct.UserMapper;
import com.fawry.bankmangementsystem.entity.Account;
import com.fawry.bankmangementsystem.entity.User;
import com.fawry.bankmangementsystem.entity.enumrole.Role;
import com.fawry.bankmangementsystem.exception.AccountException;
import com.fawry.bankmangementsystem.model.authentication.AuthenticationResponseModel;
import com.fawry.bankmangementsystem.model.authentication.LoginRequestModel;
import com.fawry.bankmangementsystem.model.authentication.RegisterRequestModel;
import com.fawry.bankmangementsystem.repository.AccountRepo;
import com.fawry.bankmangementsystem.repository.UserRepo;
import com.fawry.bankmangementsystem.security.JwtService;
import com.fawry.bankmangementsystem.service.AuthenticationService;
import com.fawry.bankmangementsystem.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepository;
    private final JwtService jwtService;
    private final AccountRepo accountRepository;
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthenticationResponseModel register(RegisterRequestModel request) {
        if (isEmailOrPhoneAlreadyExists(request.getEmail(), request.getPhone())) {
            throw new AccountException("Email or Phone Number is already exists");
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        User user =userMapper.ToUserRegister(request);
        user.setPassword(encryptedPassword);
        if (user.getRole()==null)
        {
            user.setRole(Role.USER);
        }
        userRepository.save(user);

        Account account = createAccountForUser(user);
        accountRepository.save(account);

        return AuthenticationResponseModel.builder().token(jwtService.generateToken(user)).build();
    }

    @Override
    public AuthenticationResponseModel login(LoginRequestModel request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AccountException("User " + request.getEmail() + " Not Found"));

        return AuthenticationResponseModel
                .builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    private boolean isEmailOrPhoneAlreadyExists(String email, String phone) {
        return userRepository.existsByEmail(email) || userRepository.existsByPhone(phone);
    }

    private Account createAccountForUser(User user) {
        String cardNumber = generateUniqueCardNumber();
        String cvv = Utils.generateCVV();
        return accountMapper.toAccount(cardNumber, cvv, user);
    }

    private String generateUniqueCardNumber() {
        String cardNumber = Utils.generateCardNumber();

        while (accountRepository.existsByCardNumber(cardNumber)) {
            cardNumber = Utils.generateCardNumber();
        }

        return cardNumber;
    }
}
