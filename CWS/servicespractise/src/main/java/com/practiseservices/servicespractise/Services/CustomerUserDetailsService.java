package com.practiseservices.servicespractise.Services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;


import com.practiseservices.servicespractise.Exception.UserException;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.UserRepository;


@Service
public class CustomerUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByEmail(username);

        if(user == null){
            throw new UserException("User name not found with email: "+username);
        }

        List<GrantedAuthority>authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
        
    }
    

}
