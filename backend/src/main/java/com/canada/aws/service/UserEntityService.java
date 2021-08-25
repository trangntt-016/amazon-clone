package com.canada.aws.service;


import com.canada.aws.dto.LogInResDto;
import com.canada.aws.dto.LoginReqDto;
import com.canada.aws.dto.RegisterOTPResDto;
import com.canada.aws.dto.RegisterReqDto;
import org.springframework.stereotype.Service;

@Service
public interface UserEntityService {
    RegisterOTPResDto createAnAccount(RegisterReqDto account) throws Exception;

    Boolean isOTPValid(String userId, String OTP);

    LogInResDto OTPLogin(String userId);

    LogInResDto login(LoginReqDto loginDto);

    void updateBrowsingHistory(String userId, Integer categoryId);
}
