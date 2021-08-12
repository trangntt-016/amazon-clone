package com.canada.aws.service.impl;


import com.canada.aws.api.exception.DuplicateEmailException;
import com.canada.aws.dto.RegisterOTPResDto;
import com.canada.aws.dto.RegisterReqDto;
import com.canada.aws.model.Role;
import com.canada.aws.model.UserEntity;
import com.canada.aws.repo.RoleRepository;
import com.canada.aws.repo.UserEntityRepository;
import com.canada.aws.service.UserEntityService;
import com.canada.aws.utils.MapperUtils;
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
    private JavaMailSender mailSender;

    @Autowired
    RoleRepository roleRepository;

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
                .userId(account.getName())
                .role(role)
                .name(account.getName())
                .password(encodedPassword)
                .otpCode(encodedOTP)
                .verified(false)
                .otpRequestedTime(new Date())
                .email(account.getEmail())
                .build();

        userEntityRepository.save(newUser);

        sendVerificationEmail(newUser, encodedOTP);

        RegisterOTPResDto dto = MapperUtils.mapperObject(newUser, RegisterOTPResDto.class);
        return dto;
    }

    public boolean isEmailUnique(String email){
        Optional<UserEntity>user = userEntityRepository.findByEmail(email);

        if(user.isPresent()) return false;

        return true;
    }

    public void sendVerificationEmail(UserEntity user, String OTP) throws MessagingException {
        String subject = "Verify your new Amazon account";
        String senderName = "Amazon";
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
