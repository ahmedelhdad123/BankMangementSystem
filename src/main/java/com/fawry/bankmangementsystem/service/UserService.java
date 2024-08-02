package com.fawry.bankmangementsystem.service;

import com.fawry.bankmangementsystem.dto.UserProfileDto;

public interface UserService {
    UserProfileDto getUserProfile(String email);

}
