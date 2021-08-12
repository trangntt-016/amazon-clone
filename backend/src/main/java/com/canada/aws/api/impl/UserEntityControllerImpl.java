package com.canada.aws.api.impl;

import com.canada.aws.api.UserEntityController;
import com.canada.aws.api.exception.BadCredentialsException;
import com.canada.aws.api.exception.BadRequestException;
import com.canada.aws.api.exception.DuplicateEmailException;
import com.canada.aws.dto.*;
import com.canada.aws.service.impl.UserEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserEntityControllerImpl implements UserEntityController {
    @Autowired
    UserEntityServiceImpl userService;

    @Override
    public ResponseEntity<?> register(RegisterReqDto account) {
        try{
            RegisterOTPResDto userOTP = userService.createAnAccount(account);
            return ResponseEntity.ok(userOTP);
        }
        catch(DuplicateEmailException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> verifyOTP(VerifyOTPReqDto otp) {
        try {
            userService.isOTPValid(otp.getUserId(), otp.getOtpCode());

            LogInResDto userLogin = userService.OTPLogin(otp.getUserId());

            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.add("Authorization", "Bearer " + userLogin.getJwt());
            
            return new ResponseEntity<>(
                    userLogin,
                    httpHeaders, HttpStatus.OK);
        }
        catch(BadRequestException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(BadCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<?> login(LoginReqDto userLogin) {
        return null;
    }
}
