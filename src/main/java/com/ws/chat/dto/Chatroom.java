package com.ws.chat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatroomId;

    private String roomName;

    private boolean chatroomType;

    private String chatRoomPassword;

    @OneToMany(mappedBy = "chatroom")
    @ToString.Exclude
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "chatroom")
    @ToString.Exclude
    @JsonIgnore
    private List<ChatroomUser> chatroomUsers = new ArrayList<>();

    public void addChatroomUser(ChatroomUser chatroomUser){
        chatroomUsers.add(chatroomUser);
    }
}
