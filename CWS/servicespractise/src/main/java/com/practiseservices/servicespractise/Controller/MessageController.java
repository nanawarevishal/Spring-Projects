package com.practiseservices.servicespractise.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Model.Message;
import com.practiseservices.servicespractise.Services.MessageService;
import com.practiseservices.servicespractise.Services.UserService;

@RestController
@RequestMapping("/api/message/")
public class MessageController {
    
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("create-message/{chatId}")
    public ResponseEntity<Message> createMessage(@RequestBody Message request,@PathVariable("chatId")Long chatId,@RequestHeader("Authorization")String jwt){

        Message message = messageService.createMessage(userService.findUserByJwt(jwt), chatId,request);

        return new ResponseEntity<Message>(message, HttpStatus.CREATED);
    }

    @GetMapping("chat-messages/{chatId}")
    public ResponseEntity<List<Message>> findChatsMessage(@PathVariable("chatId")Long chatId){

        List<Message>messages = messageService.findByChatsMessage(chatId, chatId);

        return new ResponseEntity<List<Message>>(messages, HttpStatus.FOUND);
    }
}
