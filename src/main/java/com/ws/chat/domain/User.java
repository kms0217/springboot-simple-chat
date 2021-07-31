package com.ws.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickname;

    @NotNull
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    @Builder.Default
    private List<ChatroomUser> chatroomUsers = new ArrayList<>();

    public void addChatroomUser(ChatroomUser chatroomUser){
        chatroomUsers.add(chatroomUser);
    }
}
