package com.practiseservices.servicespractise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practiseservices.servicespractise.Model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    
    
}
