package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.entity.Message;
import com.twd.SpringSecurityJWT.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }


    public List<Message> getMessagesByChatId(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> deleteAllMessage() {
        // Retrieve all messages before deletion
        List<Message> allMessages = messageRepository.findAll();

        if (!allMessages.isEmpty()) {
            // Delete all messages
            messageRepository.deleteAll();
        }

        // Return the list of deleted messages
        return allMessages;
    }
    public Optional<Message> deleteMessageById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            messageRepository.deleteById(id);
        }
        return message;
    }

}
