package com.ws.chat.service;

import com.ws.chat.domain.Chatroom;
import com.ws.chat.domain.ChatroomUser;
import com.ws.chat.domain.Message;
import com.ws.chat.domain.User;
import com.ws.chat.repository.ChatroomRepository;
import com.ws.chat.repository.ChatroomUserRepository;
import com.ws.chat.utils.SignalSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Configurable
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final UserService userService;
    private final ChatroomUserRepository chatroomUserRepository;
    private final ChatRoomUserService chatRoomUserService;
    private final MessageService messageService;
    private final SignalSender signalSender;

    public List<Message> allMessages(Long chatroomId){
        Chatroom chatroom = chatroomRepository.findById(chatroomId).orElseThrow();
        return chatroom.getMessages();
    }

    public Chatroom createRoom(String userName, String roomName, boolean roomType, String password) {
        User user = userService.getUserWithName(userName);
        ChatroomUser chatroomUser = new ChatroomUser();
        Chatroom chatroom = new Chatroom();
        chatroom.setRoomName(roomName);
        if (roomType){
            chatroom.setChatroomType(true);
            chatroom.setChatRoomPassword(password);
        }
        chatroomRepository.save(chatroom);
        chatroomUser.setChatroom(chatroom);
        chatroomUser.setUser(user);
        chatroomUser.setChatroomUserType(ChatroomUser.ChatroomUserType.OWNER);
        chatroomUserRepository.save(chatroomUser);
        signalSender.send("createRoom");
        return chatroom;
    }

    public Chatroom enterUser(String name, Long roomId) {
        User user = userService.getUserWithName(name);
        Chatroom chatroom = chatroomRepository.findById(roomId).orElseThrow();
        if (chatroomUserRepository.findByUserAndChatroom(user, chatroom).isPresent())
            return chatroom;
        chatRoomUserService.userJoinRoom(chatroom, user);
        messageService.makeWelcome(chatroom, user);
        return chatroom;
    }

    public List<Chatroom> getRoomsWithUserJoined(String name) {
        List<Chatroom> chatrooms = chatroomRepository.findAll();
        if(chatrooms.isEmpty())
            return chatrooms;
        User user = userService.getUserWithName(name);
        List<ChatroomUser> chatroomUsers = chatroomUserRepository.findByUser(user);
        for (Chatroom chatroom : chatrooms){
            if (!Collections.disjoint(chatroom.getChatroomUsers(), chatroomUsers)){
                chatroom.setJoined(true);
            }
        }
        return chatrooms;
    }

    public boolean checkUser(String userName, Long roomId) {
        User user = userService.getUserWithName(userName);
        Chatroom chatroom = chatroomRepository.findById(roomId).orElseThrow();
        return chatroomUserRepository.findByUserAndChatroom(user, chatroom).isPresent();
    }
}
