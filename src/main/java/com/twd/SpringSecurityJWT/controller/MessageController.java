package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.entity.Message;
import com.twd.SpringSecurityJWT.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/add/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/get/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long chatId) {
        List<Message> messages = messageService.getMessagesByChatId(chatId);
        if (messages != null && !messages.isEmpty()) {
            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
