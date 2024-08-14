package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.entity.Chat;
import com.twd.SpringSecurityJWT.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat getChat(Long id) {
        return chatRepository.findById(id).orElse(null);
    }
}

