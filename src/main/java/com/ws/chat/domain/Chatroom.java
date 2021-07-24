package com.ws.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "chatroom")
    @ToString.Exclude
    @JsonIgnore
    @Builder.Default
    private List<ChatroomUser> chatroomUsers = new ArrayList<>();

    @Transient
    private boolean joined;

    public void addChatroomUser(ChatroomUser chatroomUser){
        chatroomUsers.add(chatroomUser);
    }

    public void setJoined(boolean joined){
        this.joined = joined;
    }

    public boolean getJoined(){
        return joined;
    }
}
