package com.practiseservices.servicespractise.Services;

import java.util.List;

import com.practiseservices.servicespractise.Model.Message;
import com.practiseservices.servicespractise.Model.User;

public interface MessageService {
    
    public Message createMessage(User user,Long chatId,Message message);

    public List<Message> findByChatsMessage(Long chatId,Long userId);


}
