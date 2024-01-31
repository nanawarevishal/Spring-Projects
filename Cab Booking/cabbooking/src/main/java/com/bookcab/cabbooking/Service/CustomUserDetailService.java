package com.bookcab.cabbooking.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookcab.cabbooking.Exception.UserException;
import com.bookcab.cabbooking.Model.Driver;
import com.bookcab.cabbooking.Model.User;
import com.bookcab.cabbooking.Repository.DriverRepository;
import com.bookcab.cabbooking.Repository.UserRepository;

@Service
public class CustomUserDetailService  implements UserDetailsService{

    private UserRepository userRepository;

    private DriverRepository driverRepository;

    public CustomUserDetailService(UserRepository userRepository ,DriverRepository driverRepository){
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByEmail(username);

        if(user!=null){

            List<GrantedAuthority>authorities = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), authorities);
        }

        Driver driver = driverRepository.findByEmail(username);

        if(driver!=null){
            List<GrantedAuthority>authorities = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(driver.getEmail(),driver.getPassword(), authorities);
        }

        throw new UserException("User not found with email : "+username);
    }
    
}
