package com.canada.aws.model;


import com.canada.aws.utils.UserIdPrefixed;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    public static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes

    @Id
    @Column(name = "user_id",updatable = false,nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_gen")
    @GenericGenerator(
            name = "user_gen",
            strategy = "com.canada.aws.utils.UserIdPrefixed",
            parameters = {
                    @Parameter(name = UserIdPrefixed.INCREMENT_PARAM,value = "1"),
                    @Parameter(name = UserIdPrefixed.CODE_NUMBER_SEPARATOR_PARAMETER,value = "_"),
                    @Parameter(name = UserIdPrefixed.NUMBER_FORMAT_PARAMETER,value = "%03d")
            }
    )
    private String userId;

    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "otp_code", length = 100, nullable = true)
    private String otpCode;

    @Column(name = "otp_requested_time")
    private Date otpRequestedTime;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name="role_id",
            nullable = false,
            foreignKey=@ForeignKey(name = "FK_ROLE_USER")
    )
    private Role role;


}
