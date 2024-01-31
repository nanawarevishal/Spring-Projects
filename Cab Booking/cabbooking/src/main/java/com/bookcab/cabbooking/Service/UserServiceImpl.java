package com.bookcab.cabbooking.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookcab.cabbooking.Config.JwtProvider;
import com.bookcab.cabbooking.Domain.UserRole;
import com.bookcab.cabbooking.Exception.UserException;
import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Model.User;
import com.bookcab.cabbooking.Repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;


    public UserServiceImpl(PasswordEncoder passwordEncoder,UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        
        User createUser = new User();

        user.setEmail(user.getEmail());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setMobile(user.getMobile());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        
        User savedUser = userRepository.save(createUser);

        return savedUser;
    }

    @Override
    public User getReqUserProfile(String token) {
        
        String email = JwtProvider.getEmailFromJwtToken(token);

        // System.out.println("Email:"+email);

        User user = findUserByEmail(email);

        if(user == null){
            throw new UserException("Invalid token..!");
        }

        return user;
    }

    @Override
    public User findUserById(Long userId) {
        
        Optional<User>opt = userRepository.findById(userId);
        
        if(opt.isPresent()){
            return opt.get();
        }

        throw new UserException("User not present with id: "+userId);
    }

    @Override
    public User findUserByEmail(String email) {
        
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Ride> completeRide(Long userId) {
        
        User user = findUserById(userId);

        List<Ride>rides = userRepository.getCompletedRides(userId);

        return rides;
    }
    
}
