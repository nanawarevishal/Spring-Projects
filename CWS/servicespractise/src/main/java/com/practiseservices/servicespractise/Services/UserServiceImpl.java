package com.practiseservices.servicespractise.Services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiseservices.servicespractise.Exception.FollowingException;
import com.practiseservices.servicespractise.Exception.UserException;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFirstName(user.getFirstName());
        createdUser.setLastName(user.getLastName());
        createdUser.setPassword(user.getPassword());
        createdUser.setAddress(user.getAddress());
        
        return userRepository.save(createdUser);

    }

    @Override
    public User findUserById(Long userId) {
        
        Optional<User>user = userRepository.findById(userId);

        if(!user.isPresent()){
            throw new UserException("User not found with Id: "+userId);
        }

        return userRepository.findById(userId).get();
    }

    @Override
    public User findUserByEmail(String email) {
        User  user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("User not found with Email: "+email);
        }

        return userRepository.findByEmail(email);
    }

    @Override
    public User followUser(Long userId1, Long userId2) {

        if(!isFollowingUser(userId1, userId2)){

            User user1 = findUserById(userId1);
    
            User user2 = findUserById(userId2);
    
            user2.getFollowers().add(user1.getId());
            user1.getFollowings().add(user2.getId());
    
            userRepository.save(user1);
            userRepository.save(user2);
    
            return user1;
        }

        throw new FollowingException("User with Id : "+userId1+" has alredy followed user with Id: "+userId2);

    }

    @Override
    public User updateUser(User user,Long userId) {

        User updateUser = findUserById(userId);

        updateUser.setEmail(user.getEmail());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setPassword(user.getPassword());
        updateUser.setAddress(user.getAddress());
        
        return userRepository.save(updateUser);
    }

    @Override
    public List<User> searchUser(String query) {
        
        List<User>users = userRepository.searchUser(query);

        return users;
    }

    @Override
    public List<User> getAllFollowersUser(Long userId) {
        
        User user = findUserById(userId);

        Set<Long>userIds = user.getFollowers();

        List<User>followers = new ArrayList<>();

        for(Long id : userIds){
            followers.add(findUserById(id));
        }

        return followers;

    }

    @Override
    public List<User> getAllFollowingUser(Long userId) {
        
        
        User user = findUserById(userId);

        Set<Long>userIds = user.getFollowings();

        List<User>followings = new ArrayList<>();

        for(Long id : userIds){
            followings.add(findUserById(id));
        }

        return followings;
    }

    @Override
    public boolean isFollowingUser(Long userId1, Long userId2) {
        
        User user = findUserById(userId2);

        HashSet<Long>followersIds = user.getFollowers();

        if(followersIds.contains(userId2)){
            return true;
        }

        return false;
    }
}
