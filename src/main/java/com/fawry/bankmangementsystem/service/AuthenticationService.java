package com.fawry.bankmangementsystem.service;

import com.fawry.bankmangementsystem.model.authentication.AuthenticationResponseModel;
import com.fawry.bankmangementsystem.model.authentication.LoginRequestModel;
import com.fawry.bankmangementsystem.model.authentication.RegisterRequestModel;

public interface AuthenticationService {
     AuthenticationResponseModel register(RegisterRequestModel request);

     AuthenticationResponseModel login(LoginRequestModel request);
}
