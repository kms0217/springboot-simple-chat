package com.ws.chat.controller;

import com.ws.chat.dto.Chatroom;
import com.ws.chat.dto.Message;
import com.ws.chat.dto.User;
import com.ws.chat.repository.ChatroomRepository;
import com.ws.chat.repository.MessageRepository;
import com.ws.chat.repository.UserRepository;
import com.ws.chat.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChatroomRepository chatroomRepository;
    private final SimpMessageSendingOperations messageSendingOperations;

    // /pub/chat/message handle
    @MessageMapping("/chat/message")
    public void message(Message message, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        message.setUser(user);
        messageRepository.save(message);
        System.out.println(message);
        if(Message.MessageType.ENTER == message.getMessageType()){
            message.setContent(message.getUser().getNickname() + " 입장!");
        }
        messageSendingOperations.convertAndSend(
                "/sub/chat/room/" + message.getChatroom().getChatroomId(), message
        );
    }
}
