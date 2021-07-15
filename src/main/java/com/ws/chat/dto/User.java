package com.ws.chat.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickname;

    @NotNull
    private String password;

    @OneToMany(mappedBy = "user")
    private List<ChatroomUser> chatroomUsers = new ArrayList<>();
}
