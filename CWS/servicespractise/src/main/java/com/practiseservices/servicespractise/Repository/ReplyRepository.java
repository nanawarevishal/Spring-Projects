package com.practiseservices.servicespractise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practiseservices.servicespractise.Model.Reply;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
    
}
