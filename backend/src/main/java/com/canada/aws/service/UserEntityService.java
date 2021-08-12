package com.canada.aws.service;


import com.canada.aws.dto.RegisterOTPResDto;
import com.canada.aws.dto.RegisterReqDto;
import org.springframework.stereotype.Service;

@Service
public interface UserEntityService {
    RegisterOTPResDto createAnAccount(RegisterReqDto account) throws Exception;
}
