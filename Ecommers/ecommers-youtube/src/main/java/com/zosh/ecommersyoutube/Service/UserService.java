package com.zosh.ecommersyoutube.Service;

import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Exception.UserException;
import com.zosh.ecommersyoutube.Model.User;

@Service
public interface UserService {
    
    public User findUserById(Long id)throws UserException;

    public User findUserProfileByJWT(String JWT)throws UserException;

}
