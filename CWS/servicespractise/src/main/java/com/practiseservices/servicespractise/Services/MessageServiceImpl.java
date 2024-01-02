package com.practiseservices.servicespractise.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiseservices.servicespractise.Exception.ChatException;
import com.practiseservices.servicespractise.Model.Chat;
import com.practiseservices.servicespractise.Model.Message;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.ChatRepository;
import com.practiseservices.servicespractise.Repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Message createMessage(User user, Long chatId, Message request) {

        Chat chat = chatService.findChatById(chatId);

        Message message = new Message();
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setChat(chat);
        message.setImageUrl(request.getImageUrl());
        message.setUser(user);

        Message savedMessage = messageRepository.save(message);

        chat.getMessages().add(savedMessage);

        chatRepository.save(chat);

        return message;
    }

    @Override
    public List<Message> findByChatsMessage(Long chatId, Long userId) {

        Chat chat = chatService.findChatById(chatId);

        if(chat == null){
            throw new ChatException("Chat is not found with id: "+chatId);
        }

        List<Message>messages = messageRepository.findByChatId(chatId);

        return messages;
    }
    
}
