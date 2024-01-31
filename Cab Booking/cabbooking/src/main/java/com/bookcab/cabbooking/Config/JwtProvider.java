package com.bookcab.cabbooking.Config;


import java.util.*;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {

    private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

  
    public static String generatedToken(Authentication auth){

        String jwt = Jwts.builder()
                     .setIssuer("iCoder")
                     .setIssuedAt(new Date())
                     .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                     .claim("email", auth.getName())
                     .signWith(key)
                     .compact();

        return jwt;
    }


    public static String getEmailFromJwtToken(String jwt){

        jwt = jwt.substring(7);

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String email = String.valueOf(claims.get("email"));

        return email;
    }

    public static String getAuthoritiesFromJwtToken(String jwt){

        jwt = jwt.substring(7);

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String authorities = String.valueOf(claims.get("authorities"));

        return authorities;
    }
}