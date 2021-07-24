package com.ws.chat.domain;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatroomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatroomUserId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    private ChatroomUserType chatroomUserType;

    public void setUser(User user){
        this.user = user;
        this.user.addChatroomUser(this);
    }

    public void setChatroom(Chatroom chatroom){
        this.chatroom = chatroom;
        this.chatroom.addChatroomUser(this);
    }

    public static enum ChatroomUserType{
        OWNER,
        USER,
        BAN
    }
}
