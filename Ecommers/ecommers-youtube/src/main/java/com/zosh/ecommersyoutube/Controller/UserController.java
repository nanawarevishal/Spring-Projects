package com.zosh.ecommersyoutube.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommersyoutube.Exception.UserException;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization")String jwt)throws UserException{
        
        User user = userService.findUserProfileByJWT(jwt);

        return new ResponseEntity<User>(user, HttpStatus.FOUND);
    }
}
