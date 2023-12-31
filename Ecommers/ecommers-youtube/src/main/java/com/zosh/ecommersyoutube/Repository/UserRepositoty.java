package com.zosh.ecommersyoutube.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zosh.ecommersyoutube.Model.User;

public interface UserRepositoty extends JpaRepository<User,Long> {
    
    public User findByEmail(String email);

}
