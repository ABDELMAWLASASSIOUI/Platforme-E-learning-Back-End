package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.entity.Chat;
import com.twd.SpringSecurityJWT.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/add/chat")
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
        Chat savedChat = chatService.saveChat(chat);
        return ResponseEntity.ok(savedChat);
    }

    @GetMapping("/get/chat/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable Long id) {
        Chat chat = chatService.getChat(id);
        if (chat != null) {
            return ResponseEntity.ok(chat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
