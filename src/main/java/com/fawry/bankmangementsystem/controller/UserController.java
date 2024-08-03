package com.fawry.bankmangementsystem.controller;


import com.fawry.bankmangementsystem.dto.AccountDto;
import com.fawry.bankmangementsystem.dto.UserDto;
import com.fawry.bankmangementsystem.dto.UserProfileDto;
import com.fawry.bankmangementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    public ResponseEntity<AccountDto> createUser(@RequestBody UserDto userDto) {
        AccountDto accountDto=userService.createUser(userDto);
        return ResponseEntity.ok().body(accountDto);
    }

    @GetMapping("{email}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable("email") String email) {
        UserProfileDto user = userService.getUserProfile(email);
        return ResponseEntity.ok().body(user);
    }
}
