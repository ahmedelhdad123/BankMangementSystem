package com.fawry.bankmangementsystem.service.serviceIpml;


import com.fawry.bankmangementsystem.dto.UserProfileDto;
import com.fawry.bankmangementsystem.dto.mabstrauct.UserMapper;
import com.fawry.bankmangementsystem.entity.User;
import com.fawry.bankmangementsystem.exception.AccountException;
import com.fawry.bankmangementsystem.repository.UserRepo;
import com.fawry.bankmangementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final UserMapper userMapper;

    @Override
    public UserProfileDto getUserProfile(String email) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException("User not found"));

        return userMapper.ToUserProfileDto(user);
    }

}
