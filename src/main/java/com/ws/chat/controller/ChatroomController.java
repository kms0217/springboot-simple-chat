package com.ws.chat.controller;

import com.ws.chat.dto.Chatroom;
import com.ws.chat.dto.Message;
import com.ws.chat.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatroomController {

    private final ChatroomService chatroomService;

    @GetMapping("/room")
    public String chatPage(){
        return "chat";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<Chatroom> getAllRooms(Principal principal){
        return chatroomService.getRoomsWithUserJoined(principal.getName());
    }

    @PostMapping("/room")
    @ResponseBody
    public Chatroom newRoom(@RequestParam String roomName,
                            @RequestParam(defaultValue = "false") boolean roomType,
                            @RequestParam(defaultValue = "") String password,
                            Principal principal
    ){
        return chatroomService.createRoom(principal.getName(), roomName, roomType, password);
    }

    @GetMapping("/room/enter/{roomId}")
    @ResponseBody
    private Chatroom enterRoom(@PathVariable Long roomId, Principal principal){
        if (principal == null || principal.getName() == null)
            return null;
        return chatroomService.enterUser(principal.getName(), roomId);
    }

    @GetMapping("/room/{roomId}/messages")
    @ResponseBody
    public List<Message> allMessages(@PathVariable Long roomId){
        return chatroomService.allMessages(roomId);
    }
}
