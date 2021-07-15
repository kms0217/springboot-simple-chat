package com.ws.chat.controller;

import com.ws.chat.dto.Chatroom;
import com.ws.chat.repository.ChatroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatroomController {

    private final ChatroomRepository chatroomRepository;

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
        return chatrooms;
    }

    @PostMapping("/room")
    @ResponseBody
    public Chatroom newRoom(@RequestParam String roomName,
                            @RequestParam(defaultValue = "false") boolean roomType,
                            @RequestParam(defaultValue = "") String password){
        Chatroom chatroom = new Chatroom();
        chatroom.setRoomName(roomName);
        if (!roomType){
            chatroom.setChatroomType(true);
            chatroom.setChatRoomPassword(password);
        }
        return chatroomRepository.save(chatroom);
    }

    @GetMapping("/room/enter/{roomId}")
    private String enterRoom(Model model, @PathVariable String roomId){
        model.addAttribute("roomId", roomId);
        return "chatroom";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Chatroom findRoom(@PathVariable Long roomId){
        return chatroomRepository.findById(roomId).orElseThrow();
    }
}
