package com.practiseservices.servicespractise.Services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practiseservices.servicespractise.Config.JwtProvider;
import com.practiseservices.servicespractise.Exception.FollowingException;
import com.practiseservices.servicespractise.Exception.UserException;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFirstName(user.getFirstName());
        createdUser.setLastName(user.getLastName());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public User followUser(Long reqUserId, Long userId2) {

        if(!isFollowingUser(reqUserId, userId2)){

            User reqUser = findUserById(reqUserId);
    
            User user2 = findUserById(userId2);
    
            user2.getFollowers().add(reqUser.getId());
            reqUser.getFollowings().add(user2.getId());
    
            userRepository.save(reqUser);
            userRepository.save(user2);
    
            return reqUser;
        }

        throw new FollowingException("User with Id : "+reqUserId+" has alredy followed user with Id: "+userId2);

    }

    @Override
    public User updateUser(User user,Long userId) {

        User updateUser = findUserById(userId);

        updateUser.setEmail(user.getEmail());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        updateUser.setAddress(user.getAddress());
        if(user.getGender()!=null){
            updateUser.setGender(user.getGender());
        }
        
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

    @Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);

        User user = userRepository.findByEmail(email);

        return user;
    }
}
