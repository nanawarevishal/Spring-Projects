package com.practiseservices.servicespractise.Services;

import java.util.List;

import com.practiseservices.servicespractise.Model.User;

public interface UserService {
    

    public User createUser(User user);

    public User findUserById(Long userId);

    public User findUserByEmail(String email);

    public User followUser(Long userId1,Long userId2);

    public User updateUser(User user,Long userId);

    public List<User> searchUser(String query);

    public List<User> getAllFollowersUser(Long userId);

    public List<User> getAllFollowingUser(Long userId);

    public boolean isFollowingUser(Long userId1,Long userId2);

    public User findUserByJwt(String jwt);

    
}
