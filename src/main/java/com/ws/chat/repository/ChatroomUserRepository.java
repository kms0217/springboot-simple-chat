package com.ws.chat.repository;

import com.ws.chat.dto.Chatroom;
import com.ws.chat.dto.ChatroomUser;
import com.ws.chat.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatroomUserRepository extends JpaRepository<ChatroomUser, Long> {
    List<ChatroomUser> findByUser(User user);

    Optional<ChatroomUser> findByUserAndChatroom(User user, Chatroom chatroom);
}
