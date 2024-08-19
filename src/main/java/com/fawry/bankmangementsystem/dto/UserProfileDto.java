package com.fawry.bankmangementsystem.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
public class UserProfileDto {

    private String name;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;

    @ToString.Exclude
    private List<AccountProfileDto> accounts;
}
