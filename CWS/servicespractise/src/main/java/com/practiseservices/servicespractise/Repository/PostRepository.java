package com.practiseservices.servicespractise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.practiseservices.servicespractise.Model.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
    
    // @Query("select p from Post p where p.user.id =: userId")
    // public List<Post> findPostByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    public List<Post> findPostByUserId(@Param("userId") Long userId);

}
