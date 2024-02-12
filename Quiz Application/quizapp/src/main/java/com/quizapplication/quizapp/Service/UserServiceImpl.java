package com.quizapplication.quizapp.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizapplication.quizapp.Entity.User;
import com.quizapplication.quizapp.Exception.UserException;
import com.quizapplication.quizapp.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User createUser(User user) {
        
        User createUser = new User();

        createUser.setFirstName(user.getFirstName());
        createUser.setLastName(user.getLastName());
        createUser.setGender(user.getGender());
        createUser.setEmail(user.getEmail());
        createUser.setPassword(user.getPassword());
        
        return userRepository.save(createUser);
    }


    @Override
    public User updateUser(User user,Long userId) {
        
        User createUser = findUserById(userId);

        createUser.setFirstName(user.getFirstName());
        createUser.setLastName(user.getLastName());
        createUser.setGender(user.getGender());
        createUser.setEmail(user.getEmail());
        createUser.setPassword(user.getPassword());

        return userRepository.save(createUser);

    }

    @Override
    public User findUserById(Long userId) {
        
        Optional<User>opt = userRepository.findById(userId);

        if(opt.isPresent()){
            return opt.get();
        }

        throw new UserException("Your not exist with id: "+userId);
    }


    @Override
    public String deleteUser(Long userId) {
        
        User user = findUserById(userId);
        userRepository.delete(user);

        return "User deleted Successfully...!";
    } 
}
