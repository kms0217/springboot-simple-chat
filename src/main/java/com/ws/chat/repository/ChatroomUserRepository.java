package com.ws.chat.repository;

import com.ws.chat.dto.ChatroomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomUserRepository extends JpaRepository<ChatroomUser, Long> {
}
