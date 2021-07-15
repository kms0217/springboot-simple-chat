package com.ws.chat.repository;

import com.ws.chat.dto.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
