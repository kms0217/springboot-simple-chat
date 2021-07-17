package com.ws.chat.controller;

import com.ws.chat.dto.Message;
import com.ws.chat.dto.User;
import com.ws.chat.repository.MessageRepository;
import com.ws.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final SimpMessageSendingOperations messageSendingOperations;

    @MessageMapping("/chat/message")
    public void message(Message message, Principal principal){
        if (message.getContent() == null || message.getContent().isEmpty())
            return;
        User user = userRepository.findByUsername(principal.getName());
        message.setUser(user);
        messageRepository.save(message);
        messageSendingOperations.convertAndSend(
                "/sub/chat/room/" + message.getChatroom().getChatroomId(), message
        );
    }
}
