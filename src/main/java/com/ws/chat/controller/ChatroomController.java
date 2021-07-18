package com.ws.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatroomController {

    @GetMapping("/room")
    public String chatPage(){
        return "chat";
    }

}
