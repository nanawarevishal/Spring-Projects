package com.practiseservices.servicespractise.Services;


import java.util.List;

import com.practiseservices.servicespractise.Model.Chat;

public interface ChatService {
    
    public Chat createChat(Long userdId1,Long userId2);

    public Chat findChatById(Long chatId);

    public List<Chat> findUsersChat(Long userId);

}
