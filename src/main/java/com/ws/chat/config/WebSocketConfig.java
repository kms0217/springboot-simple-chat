package com.ws.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); // subscribe
        registry.setApplicationDestinationPrefixes("/pub"); // publish
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Websocket Connect Endpoint setting
        registry.addEndpoint("/chat", "/signal").setAllowedOriginPatterns("*").withSockJS();
    }
}
