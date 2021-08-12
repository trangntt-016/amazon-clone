package com.canada.aws.security.jwt;


import com.canada.aws.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Service
@NoArgsConstructor
public class TokenProvider {
    @Value("${secretKey}")
    private String JWT_SECRET;

    public static final int JWT_EXPIRATION_REMEMBER = 1209600000; //14 days
    public static final int JWT_EXPIRATION_WITHOUT_REMEMBER = 300000; //5 mins
    private static final String AUTHORITIES_KEY = "role";
    private static final String USERNAME = "username";
    private static final String USER_ID = "userId";

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).get(USERNAME).toString();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        // extract all claims
        Claims claims = extractAllClaims(token);
        // extract a single claim from claims
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    public boolean isValidated(String authToken) {
        // true if extracted expiry Date is after today
        return extractExpiration(authToken).after(new Date());
    }

    public String createToken(UserEntity user,boolean rememberMe) {
        Date validity;
        if(rememberMe){
            validity = new Date(System.currentTimeMillis()+JWT_EXPIRATION_REMEMBER);
        }
        else{
            validity = new Date(System.currentTimeMillis()+JWT_EXPIRATION_WITHOUT_REMEMBER);
        }

        return  Jwts.builder()
                .claim(USERNAME,user.getName())
                .claim(USER_ID,user.getUserId())
                .claim(AUTHORITIES_KEY,user.getRole().getRoleName()
                )
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET)
                .compact();
    }


    public String resolveToken(HttpServletRequest request){// decrypt token
        String bearerToken = request.getHeader("Authorization");
        if(!Objects.isNull(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}