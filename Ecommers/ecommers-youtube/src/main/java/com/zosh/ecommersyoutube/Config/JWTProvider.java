package com.zosh.ecommersyoutube.Config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTProvider {

    SecretKey key = Keys.hmacShaKeyFor(JWTConstant.secretKey.getBytes());
    
    public String generateToken(Authentication auth){
        String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+84000000))
                        .claim("email", auth.getName())
                        .signWith(key).compact();

        return jwt;
    }

    public String getEmailFromToken(String jwt){
        jwt = jwt.substring(7);

        Claims claims =Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

        String email = String.valueOf(claims.get("email"));

        return email;
    }
}
