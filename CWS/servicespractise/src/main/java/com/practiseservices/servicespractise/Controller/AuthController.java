package com.practiseservices.servicespractise.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Config.JwtProvider;
import com.practiseservices.servicespractise.Exception.UserException;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.UserRepository;
import com.practiseservices.servicespractise.Request.LoginRequest;
import com.practiseservices.servicespractise.Response.AuthResponse;
import com.practiseservices.servicespractise.Services.CustomerUserDetailsService;
import com.practiseservices.servicespractise.Services.UserService;

@RestController
@RequestMapping("/auth/")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    //  PostMapping

    @PostMapping("/signup")
    public AuthResponse createUserHandler(@RequestBody User user){

        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if(isEmailExist !=null){
            throw new UserException("User exist with email : "+user.getEmail());
        }

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFirstName(user.getFirstName());
        createdUser.setLastName(user.getLastName());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setAddress(user.getAddress());

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());

        String token = JwtProvider.generatedToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setMsg("Register Successfull....!");

        return authResponse;
      
    }

    @PostMapping("/signin")
    public AuthResponse signInHandler(@RequestBody LoginRequest loginRequests){

        Authentication authentication = authenticate(loginRequests.getEmail(), loginRequests.getPassword());

        String token = JwtProvider.generatedToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setMsg("SignIn Successfull...!");

        return response;
    }

    private Authentication authenticate(String email,String password){

        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

        if(userDetails==null){
            throw new UserException("User not found with email: "+email);
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }


}
