package com.practiseservices.servicespractise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practiseservices.servicespractise.Model.Message;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message,Long> {

    public List<Message> findByChatId(Long chatId);
}
