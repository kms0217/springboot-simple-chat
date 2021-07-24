package com.ws.chat.controller;

import com.ws.chat.domain.Chatroom;
import com.ws.chat.domain.Message;
import com.ws.chat.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatroomService chatroomService;

    @GetMapping("/rooms")
    public List<Chatroom> getAllRooms(Principal principal){
        return chatroomService.getRoomsWithUserJoined(principal.getName());
    }

    @PostMapping("/room")
    public Chatroom newRoom(@RequestParam String roomName,
                            @RequestParam(defaultValue = "false") boolean roomType,
                            @RequestParam(defaultValue = "") String password,
                            Principal principal
    ){
        return chatroomService.createRoom(principal.getName(), roomName, roomType, password);
    }

    @GetMapping("/room/enter/{roomId}")
    public Chatroom enterRoom(@PathVariable Long roomId, Principal principal){
        return chatroomService.enterUser(principal.getName(), roomId);
    }

    @GetMapping("/room/{roomId}/messages")
    public List<Message> allMessages(@PathVariable Long roomId){
        return chatroomService.allMessages(roomId);
    }

    @GetMapping("/room/joined/{roomId}")
    public boolean checkJoin(@PathVariable Long roomId, Principal principal){
        if (chatroomService.checkUser(principal.getName(), roomId)){
            return true;
        } else{
            return false;
        }
    }
}
