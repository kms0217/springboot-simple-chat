package com.ws.chat.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatroomId;

    private String roomName;

    private boolean chatroomType;

    private String chatRoomPassword;

    @OneToMany(mappedBy = "chatroom")
    private List<Message> messages;

    @OneToMany(mappedBy = "chatroom")
    private List<ChatroomUser> chatroomUserList = new ArrayList<>();
}
