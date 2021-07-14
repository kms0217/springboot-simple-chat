package com.ws.chat.repository;

import com.ws.chat.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByNickname(String nickname);
}
