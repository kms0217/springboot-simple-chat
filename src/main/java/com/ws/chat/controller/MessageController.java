package com.ws.chat.controller;

import com.ws.chat.dto.Message;
import com.ws.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat/message")
    public void message(Message message, Principal principal){
        if (message.getContent() == null || message.getContent().isEmpty())
            return;
        messageService.newMessage(principal.getName(), message);
    }
}
