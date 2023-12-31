package com.practiseservices.servicespractise.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.UserRepository;
import com.practiseservices.servicespractise.Services.UserService;



@RestController
@RequestMapping("/userAPI")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    //  PostMapping

    @PostMapping("/createUser")
    public User createUserHandler(@RequestBody User user){
       
        User createUser = userService.createUser(user);

        return createUser;
    }

    // Putmapping 

    @PutMapping("/updateUser/{id}")
    public User updateUserHandler(@PathVariable("id")Long userId, @RequestBody User user){
          
        User updatedUser = userService.updateUser(user,userId);

        return updatedUser;
    }

    // GetMapping
    @GetMapping("/getUser/{id}")
    public User findUserByIdhandler(@PathVariable("id")Long userId){

        User user = userService.findUserById(userId);

        return user;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUserHandler(){
        List<User>user = userRepository.findAll();

        return new ResponseEntity<List<User>>(user,HttpStatus.FOUND);
    }

    @PutMapping("/followUser/{userId1}/{userId2}")
    public User followUserHandler(@PathVariable("userId1")Long userId1,@PathVariable("userId2")Long userId2){

        User user = userService.followUser(userId1, userId2);

        return user;

    }

    @GetMapping("/searchUser")
    public List<User> searchUserHandler(@RequestParam("query") String query){

        List<User> users = userRepository.searchUser(query);
        return users;
    }

    @GetMapping("/followersUser/{id}")
    public List<User> getFollowerHandler(@PathVariable("id")Long userId){

        List<User>followers = userService.getAllFollowersUser(userId);

        return followers;
    }

    @GetMapping("/followingsUser/{id}")
    public List<User> getFollowingHandler(@PathVariable("id")Long userId){

        List<User>followings = userService.getAllFollowingUser(userId);

        return followings;
    }

}
