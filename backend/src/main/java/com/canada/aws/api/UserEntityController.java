package com.canada.aws.api;

import com.canada.aws.dto.LoginReqDto;
import com.canada.aws.dto.RegisterReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/users")
public interface UserEntityController {
    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterReqDto account) throws Exception;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginReqDto userLogin);

}
