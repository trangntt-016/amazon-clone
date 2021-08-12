package com.canada.aws.service.impl;


import com.canada.aws.api.exception.BadCredentialsException;
import com.canada.aws.api.exception.BadRequestException;
import com.canada.aws.api.exception.DuplicateEmailException;
import com.canada.aws.dto.LogInResDto;
import com.canada.aws.dto.LoginReqDto;
import com.canada.aws.dto.RegisterOTPResDto;
import com.canada.aws.dto.RegisterReqDto;
import com.canada.aws.model.Role;
import com.canada.aws.model.UserEntity;
import com.canada.aws.repo.RoleRepository;
import com.canada.aws.repo.UserEntityRepository;
import com.canada.aws.security.jwt.TokenProvider;
import com.canada.aws.service.UserEntityService;
import com.canada.aws.utils.MapperUtils;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Optional;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public RegisterOTPResDto createAnAccount(RegisterReqDto account) throws MessagingException {
        if(!isEmailUnique(account.getEmail())) {
            throw new DuplicateEmailException("Email " + account.getEmail() + " already exists!");
        }

        String encodedPassword = passwordEncoder.encode(account.getPassword());

        String encodedOTP = generateRandomOTP();

        Role role = roleRepository.findByRoleName(account.getRole()).get();

        UserEntity newUser = UserEntity.builder()
                .role(role)
                .name(account.getName())
                .password(encodedPassword)
                .otpCode(encodedOTP)
                .verified(false)
                .otpRequestedTime(new Date())
                .email(account.getEmail())
                .build();

        UserEntity savedUser = userEntityRepository.save(newUser);

        sendVerificationEmail(savedUser, encodedOTP);

        RegisterOTPResDto dto = MapperUtils.mapperObject(savedUser, RegisterOTPResDto.class);
        return dto;
    }

    @Override
    public Boolean isOTPValid(String userId, String OTP) {
        Optional<UserEntity>user = userEntityRepository.findById(userId);

        if(user.isEmpty()) throw new BadRequestException("Unable to find user with id " + userId);

        if(user.get().getOtpCode()==null || !user.get().getOtpCode().equals(OTP)) throw new BadCredentialsException("OTP does not exist");

        long currentTimeInMillis = System.currentTimeMillis();

        long otpRequestedTimeInMillis = user.get().getOtpRequestedTime().getTime();

        if (otpRequestedTimeInMillis + UserEntity.OTP_VALID_DURATION < currentTimeInMillis) {
            // OTP expires
            throw new BadCredentialsException("OTP has expired");
        }
        user.get().setVerified(true);

        user.get().setOtpCode(null);

        user.get().setOtpRequestedTime(null);

        userEntityRepository.save(user.get());

        return true;
    }

    @Override
    public LogInResDto OTPLogin(String userId) {
        Optional<UserEntity> userFound = userEntityRepository.findById(userId);

        if(userFound.isPresent()){
            LoginReqDto loginReq = MapperUtils.mapperObject(userFound.get(), LoginReqDto.class);

            loginReq.setIsRememberMe(true);

            return login(loginReq);
        }
        return null;
    }

    @Override
    public LogInResDto login(LoginReqDto loginDto) {
        Optional<UserEntity> userFound = userEntityRepository.findByEmail(loginDto.getEmail());

        if(userFound.isEmpty()) {
            throw new BadRequestException("Unable to find user with email " + loginDto.getEmail());
        }

        String jwt = tokenProvider.createToken(userFound.get(),loginDto.getIsRememberMe());

        String name = userFound.get().getName();

        LogInResDto userLoginDto = LogInResDto.builder().jwt(jwt).name(name).isRememberMe(loginDto.getIsRememberMe()).build();

        return userLoginDto;
    }

    public boolean isEmailUnique(String email){
        Optional<UserEntity>user = userEntityRepository.findByEmail(email);

        if(user.isPresent()) return false;

        return true;
    }

    public void sendVerificationEmail(UserEntity user, String OTP) throws MessagingException {
        String subject = "Verify your new Amazon account";
        String content = "<div style=\"width: 500px;\">\n" +
                "  <div style=\"display:flex; flex-direction: row; justify-content: space-between;margin-bottom:10px\">\n" +
                "    <img style = \"object-fit: contain;width: 100px;margin-right:150px\"src = \"https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Amazon_logo.svg/1024px-Amazon_logo.svg.png\">\n" +
                "    <h2 style=\"font-weight: 300\">Verify your new Amazon account</h2>\n" +
                "  </div>\n" +
                "  <hr>\n" +
                "  <div style=\"margin-top:10px;text-align: justify\">\n" +
                "    <p>To verify your email address, please use the following One Time Password (OTP):</p>\n" +
                "    <br>\n" +
                "    <h2>" + OTP + "</h2>\n" +
                "    <br>\n" +
                "    <p>Do not share this OTP with anyone. Amazon takes your account security very seriously. Amazon Customer Service will never ask you to disclose or verify your Amazon password, OTP, credit card, or banking account number. If you receive a suspicious email with a link to update your account information, do not click on the link—instead, report the email to Amazon for investigation.</p>\n" +
                "    <br>\n" +
                "    <p>Thank you</p>\n" +
                "  </div>\n" +
                "</div>\n";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        mailSender.send(message);
    }

    public String generateRandomOTP(){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 5; i++) {
            int randomNum = (int) (Math.random()*(9-0)+0);
            String key = String.valueOf(randomNum);
            sb.append(key);
        }

        return sb.toString();
    }
}
