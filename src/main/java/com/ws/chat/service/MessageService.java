package com.ws.chat.service;

import com.ws.chat.domain.Chatroom;
import com.ws.chat.domain.Message;
import com.ws.chat.domain.User;
import com.ws.chat.exception.ApiException;
import com.ws.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Configurable
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final SimpMessageSendingOperations messageSendingOperations;

    public void makeWelcome(Chatroom chatroom, User user) {
        Message message = new Message();
        message.setContent(user.getNickname() + " 입장!");
        message.setUser(user);
        message.setChatroom(chatroom);
        message.setMessageType(Message.MessageType.ENTER);
        messageRepository.save(message);
        messageSendingOperations.convertAndSend(
                "/sub/chat/room/" + chatroom.getChatroomId(), message
        );
    }

    public void newMessage(String userName, Message message) {
        User user = userService.getUserWithName(userName);
        if (user == null)
            throw new ApiException("존재하지 않는 user 입니다.");
        message.setUser(user);
        messageRepository.save(message);
        messageSendingOperations.convertAndSend(
                "/sub/chat/room/" + message.getChatroom().getChatroomId(), message
        );
    }
}
