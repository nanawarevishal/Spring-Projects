package com.practiseservices.servicespractise.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Model.Chat;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Request.ChatRequest;
import com.practiseservices.servicespractise.Services.ChatService;
import com.practiseservices.servicespractise.Services.UserService;

@RestController
@RequestMapping("/api/chat/")
public class ChatController {
    
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("chats")
    public ResponseEntity<Chat> createChatHandler(@RequestHeader("Authorization")String jwt,@RequestBody ChatRequest request){

        User user = userService.findUserById(request.getUserId());

        System.out.println("user: "+user.getFirstName());

        User reqUser = userService.findUserByJwt(jwt);
        System.out.println("Req User: "+reqUser.getFirstName());

        Chat chat = chatService.createChat(reqUser.getId(),user.getId());

        return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);
    }

    @GetMapping("user-chats")
    public ResponseEntity<List<Chat>> findUsersChat(@RequestHeader("Authorization")String jwt){
        
        List<Chat>chats = chatService.findUsersChat(userService.findUserByJwt(jwt).getId());

        return new ResponseEntity<List<Chat>>(chats, HttpStatus.FOUND);
    }



}
