package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.entity.Message;
import com.twd.SpringSecurityJWT.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/get/chat/{chatId}")//not work
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long chatId) {
        List<Message> messages = messageService.getMessagesByChatId(chatId);
        if (messages != null && !messages.isEmpty()) {
            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/get/messages")//not work
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        if (messages != null && !messages.isEmpty()) {
            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/deleteAll/message")//is work
    public ResponseEntity<String> deleteMessage() {
        List<Message> deletedMessages = messageService.deleteAllMessage();

        if (deletedMessages != null && !deletedMessages.isEmpty()) {
            // Messages were successfully deleted
            return ResponseEntity.ok("Messages successfully deleted.");
        } else {
            // No messages were deleted
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No messages found to delete.");
        }
    }

    @DeleteMapping("/delete/message/{id}")//is work
    public ResponseEntity<String> deleteMessageById(@PathVariable Long id) {
        Optional<Message> deletedMessage = messageService.deleteMessageById(id);
        if (deletedMessage.isPresent()) {
            return ResponseEntity.ok("Message with ID " + id + " successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message with ID " + id + " not found.");
        }

    }
}
