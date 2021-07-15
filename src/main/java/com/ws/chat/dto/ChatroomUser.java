package com.ws.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatroomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatroomUserId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    private ChatroomUserType chatroomUserType;

    public static enum ChatroomUserType{
        OWNER,
        USER,
        BAN
    }
}
