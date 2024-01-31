package com.bookcab.cabbooking.Config;

import java.io.IOException;
import java.util.*;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        System.out.println(jwt);

        if(jwt!=null){

            try {
                
                // jwt = jwt.substring("Bearer ".length());
                // System.out.println(jwt);
                // String email = JwtProvider.getEmailFromJwtToken(jwt);
                // System.out.println(email);
                
                // System.out.println(authority);
                
                // List<GrantedAuthority>authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);

                // System.out.println(authorities);

                // Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,authorities);

                // SecurityContextHolder.getContext().setAuthentication(authentication);
                String email = JwtProvider.getEmailFromJwtToken(jwt);
                String authority = JwtProvider.getAuthoritiesFromJwtToken(jwt);

                List<GrantedAuthority>authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);


            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token...!");
            }
        }


        filterChain.doFilter(request, response);
    }

    // protected boolean shouldNotFilter(HttpServletRequest request)throws ServletException{

    //     return request.getServletPath().equals("/api/auth/user/");
    // }
    
}
