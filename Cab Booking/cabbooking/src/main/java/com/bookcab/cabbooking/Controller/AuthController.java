package com.bookcab.cabbooking.Controller;

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

import com.bookcab.cabbooking.Config.JwtProvider;
import com.bookcab.cabbooking.Domain.UserRole;
import com.bookcab.cabbooking.Exception.DriverException;
import com.bookcab.cabbooking.Exception.UserException;
import com.bookcab.cabbooking.Model.Driver;
import com.bookcab.cabbooking.Model.User;
import com.bookcab.cabbooking.Repository.DriverRepository;
import com.bookcab.cabbooking.Repository.UserRepository;
import com.bookcab.cabbooking.Request.DriverSignUpRequest;
import com.bookcab.cabbooking.Request.LoginRequest;
import com.bookcab.cabbooking.Request.SignUpRequest;
import com.bookcab.cabbooking.Response.JwtResponse;
import com.bookcab.cabbooking.Service.CustomUserDetailService;
import com.bookcab.cabbooking.Service.DriverService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    
    private UserRepository userRepository;

    private DriverRepository driverRepository;

    private PasswordEncoder passwordEncoder;

    private CustomUserDetailService customUserDetailService;

    private DriverService driverService;

    public AuthController(UserRepository userRepository,DriverRepository driverRepository,PasswordEncoder passwordEncoder,JwtProvider jwtprovider,CustomUserDetailService customUserDetailService,DriverService driverService){
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailService = customUserDetailService;
        this.driverService = driverService;
    }

    @PostMapping("user/signup")
    public ResponseEntity<JwtResponse> signUp(@RequestBody SignUpRequest request){

        String email = request.getEmail();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String mobile = request.getMobile();
        String password = request.getPassword();

        User user = userRepository.findByEmail(email);

        if(user==null){
            
            String encodedPassword = passwordEncoder.encode(password);

            User createUser = new User();
            createUser.setFirstName(firstName);
            createUser.setLastName(lastName);
            createUser.setEmail(email);
            createUser.setPassword(encodedPassword);
            createUser.setMobile(mobile);
            createUser.setRole(UserRole.USER);

            User savedUser = userRepository.save(createUser);

            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = JwtProvider.generatedToken(authentication);

            JwtResponse response = new JwtResponse();
            response.setAuthenticated(true);
            response.setJwt(jwt);
            response.setError(false);
            response.setErrorDetails(null);
            response.setType(UserRole.USER);
            response.setMsg("Account Created Successfully...!"+savedUser.getFirstName());

            return new ResponseEntity<JwtResponse>(response, HttpStatus.ACCEPTED);

        }
        throw new UserException("User already exist with email: "+email);
        
    }

    @PostMapping("driver/signup")
    public ResponseEntity<JwtResponse> driverSignUpHandler(@RequestBody DriverSignUpRequest request){

        Driver driver = driverRepository.findByEmail(request.getEmail());

        if(driver == null){

            Driver createDriver = driverService.registerDriver(request);
            

            Authentication authentication = new UsernamePasswordAuthenticationToken(createDriver.getEmail(), createDriver.getPassword());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = JwtProvider.generatedToken(authentication);

            JwtResponse response = new JwtResponse();
            response.setAuthenticated(true);
            response.setJwt(jwt);
            response.setError(false);
            response.setErrorDetails(null);
            response.setType(UserRole.USER);
            response.setMsg("Account Created Successfully...!"+createDriver.getFirstName());

            return new ResponseEntity<JwtResponse>(response, HttpStatus.ACCEPTED);

        }

        throw new DriverException("Driver already exist with email: "+request.getEmail());
    }


    @PostMapping("user/signin")
    public ResponseEntity<JwtResponse> signInUserHandler(@RequestBody LoginRequest request){

        String userName = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticate(userName,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = JwtProvider.generatedToken(authentication);

        JwtResponse response = new JwtResponse();
        response.setAuthenticated(true);
        response.setJwt(jwt);
        response.setError(false);
        response.setErrorDetails(null);
        response.setType(UserRole.USER);
        response.setMsg("Account Login Successfully...!"+userName);

        return new ResponseEntity<JwtResponse>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("driver/signin")
    public ResponseEntity<JwtResponse> signDriverHandler(@RequestBody LoginRequest request){

        Authentication authentication = authenticate(request.getEmail(),request.getPassword());
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generatedToken(authentication);
        
        JwtResponse response = new JwtResponse();
        response.setAuthenticated(true);
        response.setJwt(jwt);
        response.setError(false);
        response.setErrorDetails(null);
        response.setType(UserRole.DRIVER);
        response.setMsg("Account Login Successfully...!"+request.getEmail());

        return new ResponseEntity<JwtResponse>(response, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String userName,String password){

        UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username: "+userName);
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password: "+password);
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

    }

    
}
