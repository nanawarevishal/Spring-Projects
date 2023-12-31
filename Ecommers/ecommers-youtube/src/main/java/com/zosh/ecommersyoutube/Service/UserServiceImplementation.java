package com.zosh.ecommersyoutube.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Config.JWTProvider;
import com.zosh.ecommersyoutube.Exception.UserException;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Repository.UserRepositoty;


@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepositoty userRepositoty;

    @Autowired 
    private JWTProvider jwtProvider;



    @Override
    public User findUserById(Long id) throws UserException {
        Optional<User> user = userRepositoty.findById(id);

        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("User not found with id: "+id);
    }

    @Override
    public User findUserProfileByJWT(String JWT) throws UserException {
        
        String email = jwtProvider.getEmailFromToken(JWT);

        User user = userRepositoty.findByEmail(email);

        if(user==null){
            throw new UserException("User not found with email: "+email);
        }

        return user;
    }
    
}
