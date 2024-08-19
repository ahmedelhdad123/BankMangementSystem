package com.fawry.bankmangementsystem.dto.mabstrauct;

import com.fawry.bankmangementsystem.dto.UserDto;
import com.fawry.bankmangementsystem.dto.UserProfileDto;
import com.fawry.bankmangementsystem.entity.User;
import com.fawry.bankmangementsystem.model.authentication.RegisterRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto ToDto(User user);
    User ToUser(UserDto userDto);


    User ToUserRegister(RegisterRequestModel registerRequestModel);


    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.accounts", target = "accounts")
    UserProfileDto ToUserProfileDto(User user);
    User ProfileDtoToUser(UserProfileDto userProfileDto);
}
