package com.bookcab.cabbooking.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Model.User;
import com.bookcab.cabbooking.Service.UserService;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("get-by-id/{userId}")
    public ResponseEntity<User> findUserByIdHandler(@PathVariable("userId")Long userId){
        
        User user = userService.findUserById(userId);

        return new ResponseEntity<User>(user,HttpStatus.FOUND);
    }

    @GetMapping("profile")
    public ResponseEntity<User> getUserProfileByToken(@RequestHeader("Authorization")String jwt){

        User user = userService.getReqUserProfile(jwt);
        System.out.println("User: "+user);

        return new ResponseEntity<User>(user,HttpStatus.FOUND);
    }

    @GetMapping("all-rides")
    public ResponseEntity<List<Ride>> getcompleteRideHandler(@RequestHeader("Authorization")String jwt){

        User user = userService.getReqUserProfile(jwt);
        List<Ride>rides = userService.completeRide(user.getId());

        return new ResponseEntity<List<Ride>>(rides,HttpStatus.ACCEPTED);
    }

}
