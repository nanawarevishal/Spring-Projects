package com.bookcab.cabbooking.Service;

import java.util.List;

import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Model.User;

public interface UserService {
    
    public User createUser(User user);

    public User getReqUserProfile(String token);

    public User findUserById(Long userId);

    public User findUserByEmail(String email);

    public List<Ride> completeRide(Long userId);
}
