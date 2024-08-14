package com.twd.SpringSecurityJWT.repository;

import com.twd.SpringSecurityJWT.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByChatId(Long chatId);

    Message deleteMessageById(Long id);
}
