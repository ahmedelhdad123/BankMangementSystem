package com.fawry.bankmangementsystem.controller;



import com.fawry.bankmangementsystem.model.response.ResponseModel;
import com.fawry.bankmangementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping
    public ResponseEntity<ResponseModel> getUserProfile() {
        return ResponseEntity.ok(
                ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(userService.getUserProfile())
                        .build()
        );
    }
}
