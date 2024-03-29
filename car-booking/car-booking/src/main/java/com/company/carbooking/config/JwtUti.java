package com.company.carbooking.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
@Service
public class JwtUti {
    SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.JWT_KEY.getBytes());

    public String generateToken(Authentication authentication){
        JwtBuilder builder = Jwts.builder();
        builder.setIssuer("carbooking");
        builder.setIssuedAt(new Date());
        builder.setExpiration(new Date(new Date().getTime() + 8600000));
        builder.claim("email",authentication.getName());
        builder.signWith(secretKey);
        return builder.compact();
    }
    public String getEmailFromToken(String jwt){
        jwt = jwt.replace("Bearer ","");
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody().get("email",String.class);
    }
}
