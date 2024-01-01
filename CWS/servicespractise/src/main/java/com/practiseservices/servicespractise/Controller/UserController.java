package com.practiseservices.servicespractise.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.UserRepository;
import com.practiseservices.servicespractise.Services.UserService;



@RestController
// @RequestMapping("/userAPI")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    //  PostMapping

    // @PostMapping("/createUser")
    // public User createUserHandler(@RequestBody User user){
       
    //     User createUser = userService.createUser(user);

    //     return createUser;
    // }

    // Putmapping 

    @PutMapping("/api/updateUser")
    public User updateUserHandler(@RequestHeader("Authorization")String jwt, @RequestBody User user){

        User updatedUser = userService.updateUser(user,userService.findUserByJwt(jwt).getId());

        return updatedUser;
    }

    // GetMapping
    @GetMapping("/api/getUser")
    public User findUserByIdhandler(@RequestHeader("Authorization")String jwt){

        User user = userService.findUserById(userService.findUserByJwt(jwt).getId());

        return user;
    }

    @GetMapping("/api/getAll")
    public ResponseEntity<List<User>> getAllUserHandler(){
        List<User>user = userRepository.findAll();

        return new ResponseEntity<List<User>>(user,HttpStatus.FOUND);
    }

    @PutMapping("/api/followUser/{userId2}")
    public User followUserHandler(@RequestHeader("Authorization")String jwt,@PathVariable("userId2")Long userId2){

        User user = userService.followUser(userService.findUserByJwt(jwt).getId(), userId2);

        return user;

    }

    @GetMapping("/api/searchUser")
    public List<User> searchUserHandler(@RequestParam("query") String query){

        List<User> users = userRepository.searchUser(query);
        return users;
    }

    @GetMapping("/api/followersUser/{id}")
    public List<User> getFollowerHandler(@PathVariable("id")Long userId){

        List<User>followers = userService.getAllFollowersUser(userId);

        return followers;
    }

    @GetMapping("/api/followingsUser/{id}")
    public List<User> getFollowingHandler(@PathVariable("id")Long userId){

        List<User>followings = userService.getAllFollowingUser(userId);

        return followings;
    }

    @GetMapping("/api/user/profile")
    public User getUserFromToken(@RequestHeader("Authorization")String jwt) {
        User user = userService.findUserByJwt(jwt);

        return user;
    }

}
