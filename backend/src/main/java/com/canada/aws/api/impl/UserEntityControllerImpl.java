package com.canada.aws.api.impl;

import com.canada.aws.api.UserEntityController;
import com.canada.aws.api.exception.DuplicateEmailException;
import com.canada.aws.dto.LoginReqDto;
import com.canada.aws.dto.RegisterOTPResDto;
import com.canada.aws.dto.RegisterReqDto;
import com.canada.aws.service.impl.UserEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> login(LoginReqDto userLogin) {
        return null;
    }
}
