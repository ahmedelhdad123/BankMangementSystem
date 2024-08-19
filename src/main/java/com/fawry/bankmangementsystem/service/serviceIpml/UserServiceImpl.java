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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final UserMapper userMapper;


    @Override
    public UserProfileDto getUserProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = findUserByEmail(email);
        return userMapper.ToUserProfileDto(user);
    }

    //////////////////////////////////////////////////////////////////////


    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException("User not found"));
    }

    //////////////////////////////////////////////////////////////////////////////////
}