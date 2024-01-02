package com.practiseservices.servicespractise.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiseservices.servicespractise.Exception.ChatException;
import com.practiseservices.servicespractise.Model.Chat;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Override
    public Chat createChat(Long userdId1, Long userId2) {

        User reqUser = userService.findUserById(userdId1);

        User user2 = userService.findUserById(userId2);

        Chat isChatExist = chatRepository.findChatByUsersId(user2, reqUser);
        
        if(isChatExist!=null){
            // isChatExist.setTimeStamp(LocalDateTime.now());
            // chatRepository.save(isChatExist);
            return isChatExist;
        }

        Chat chat = new Chat();

        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);
        chat.setTimeStamp(LocalDateTime.now());

        chatRepository.save(chat);

        return chat;

    }

    @Override
    public Chat findChatById(Long chatId) {
        Optional<Chat>opt = chatRepository.findById(chatId);

        if(opt.isPresent()){
            return opt.get();
        }

        throw new ChatException("Char is not exist's with id: "+chatId);
    }

    @Override
    public List<Chat> findUsersChat(Long userId) {
        
        User user = userService.findUserById(userId);

        List<Chat>chats = chatRepository.findByUsersId(userId);

        return chats;
    }
    
}
