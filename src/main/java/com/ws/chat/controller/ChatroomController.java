package com.ws.chat.controller;

import com.ws.chat.dto.Chatroom;
import com.ws.chat.dto.ChatroomUser;
import com.ws.chat.dto.Message;
import com.ws.chat.dto.User;
import com.ws.chat.repository.ChatroomRepository;
import com.ws.chat.repository.ChatroomUserRepository;
import com.ws.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatroomController {

    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;
    private final ChatroomUserRepository chatroomUserRepository;

    @GetMapping("/room")
    public String chatPage(){
        return "chat";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<Chatroom> getAllRooms(){
        List<Chatroom> chatrooms = chatroomRepository.findAll();
        if(chatrooms == null){
            return new ArrayList<>();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        List<ChatroomUser> chatroomUsers = chatroomUserRepository.findByUser(user);
        for (Chatroom chatroom : chatrooms){
            if (!Collections.disjoint(chatroom.getChatroomUsers(), chatroomUsers)){
                chatroom.setJoined(true);
            }
        }
        return chatrooms;
    }

    @PostMapping("/room")
    @ResponseBody
    public Chatroom newRoom(@RequestParam String roomName,
                            @RequestParam(defaultValue = "false") boolean roomType,
                            @RequestParam(defaultValue = "") String password
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());

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
        return chatroom;
    }

    @GetMapping("/room/enter/{roomId}")
    @ResponseBody
    private Chatroom enterRoom(Model model, @PathVariable Long roomId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        Chatroom chatroom = chatroomRepository.findById(roomId).orElseThrow();
        ChatroomUser chatroomUser = new ChatroomUser();
        chatroomUser.setChatroom(chatroom);
        chatroomUser.setUser(user);
        chatroomUserRepository.save(chatroomUser);
        return chatroom;
    }

    @GetMapping("/room/{roomId}/messages")
    @ResponseBody
    public List<Message> allMessages(@PathVariable Long roomId){
        Chatroom chatroom = chatroomRepository.findById(roomId).orElseThrow();
        return chatroom.getMessages();
    }
}
