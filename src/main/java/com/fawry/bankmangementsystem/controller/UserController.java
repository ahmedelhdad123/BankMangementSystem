package com.fawry.bankmangementsystem.controller;


import com.fawry.bankmangementsystem.dto.UserProfileDto;
import com.fawry.bankmangementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{email}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable("email") String email) {
        UserProfileDto user = userService.getUserProfile(email);
        return ResponseEntity.ok().body(user);
    }
}
