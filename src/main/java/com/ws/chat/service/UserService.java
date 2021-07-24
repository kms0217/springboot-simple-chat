package com.ws.chat.service;

import com.ws.chat.domain.User;
import com.ws.chat.dto.UserRequest;
import com.ws.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Configurable
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
    }

    // SignUp에서 온 UserRequest를 Site User로 변환
    public User save(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setNickname(userRequest.getNickname());
        user.setPassword(encoder.encode(userRequest.getPassword()));
        return repository.save(user);
    }

    public User getUserWithName(String userName){
        return repository.findByUsername(userName);
    }

    public User getUserWithNickName(String nickname){
        return repository.findByNickname(nickname);
    }
}