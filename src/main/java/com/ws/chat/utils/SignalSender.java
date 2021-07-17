package com.ws.chat.utils;

import com.ws.chat.dto.Signal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignalSender {

    private final SimpMessageSendingOperations messageSendingOperations;

    public void send(String msg){
        System.out.println("ssssssssssssssssssssssssssssssssssssss");
        Signal signal = new Signal();
        signal.setMsg(msg);
        messageSendingOperations.convertAndSend(
                "/sub/signal", signal
        );
    }
}
