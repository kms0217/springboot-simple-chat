package com.ws.chat.service;

import com.ws.chat.domain.Chatroom;
import com.ws.chat.domain.ChatroomUser;
import com.ws.chat.domain.User;
import com.ws.chat.repository.ChatroomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Configurable
public class ChatRoomUserService {

    private final ChatroomUserRepository chatroomUserRepository;

    public void userJoinRoom(Chatroom chatroom, User user) {
        ChatroomUser chatroomUser = new ChatroomUser();
        chatroomUser.setChatroom(chatroom);
        chatroomUser.setUser(user);
        chatroomUserRepository.save(chatroomUser);
    }
}
