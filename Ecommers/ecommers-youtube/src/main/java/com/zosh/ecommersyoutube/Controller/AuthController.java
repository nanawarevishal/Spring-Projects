package com.zosh.ecommersyoutube.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommersyoutube.Config.JWTProvider;
import com.zosh.ecommersyoutube.Exception.UserException;
import com.zosh.ecommersyoutube.Model.Cart;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Repository.UserRepositoty;
import com.zosh.ecommersyoutube.Request.LoginRequest;
import com.zosh.ecommersyoutube.Response.AuthResponse;
import com.zosh.ecommersyoutube.Service.CartService;
import com.zosh.ecommersyoutube.Service.CustomUserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepositoty userRepositoty;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserServiceImpl customUserServiceImpl;

    @Autowired
    private CartService cartService;
    
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)throws UserException{
        
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExists = userRepositoty.findByEmail(email);

        if(isEmailExists!=null){
            throw new UserException("Email is already used with another account");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setCreatedAt(LocalDateTime.now());

        User saveduser =  userRepositoty.save(createdUser);

        Cart cart = cartService.createCart(saveduser);
        

        Authentication authentication = new UsernamePasswordAuthenticationToken(saveduser.getEmail(), saveduser.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token,"Signup Success");

        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){

        String userName = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(userName,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token,"Signin Success");

        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);

    }

    private Authentication authenticate(String userName,String password){

        UserDetails userDetails = customUserServiceImpl.loadUserByUsername(userName);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid Username....!");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password....!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
    
}
