package com.ws.chat.utils;

import com.ws.chat.dto.UserRequest;
import com.ws.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequest userRequest = (UserRequest) target;
        if(!(userRequest.getPassword().equals(userRequest.getPasswordConfirm()))){
            errors.rejectValue("password", "key","비밀번호가 일치하지 않습니다.");
        } else if(userRepository.findByUsername(userRequest.getUsername()) !=null){
            errors.rejectValue("username", "key","중복되는 ID가 존재합니다.");
        } else if(userRepository.findByNickname(userRequest.getNickname()) !=null){
            errors.rejectValue("nickname", "key", "중복되는 닉네임이 존재합니다.");
        }
    }
}
