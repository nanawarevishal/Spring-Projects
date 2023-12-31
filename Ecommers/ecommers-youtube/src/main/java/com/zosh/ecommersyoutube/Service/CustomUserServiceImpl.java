package com.zosh.ecommersyoutube.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Repository.UserRepositoty;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    
    @Autowired
    private UserRepositoty userRepositoty;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepositoty.findByEmail(username);

        if(user==null){

            throw new UsernameNotFoundException("User not found with email....!");
        }

        List<GrantedAuthority>authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
    
}
