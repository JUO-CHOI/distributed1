package com.konkuk.mafia.controller;


import com.konkuk.mafia.dto.ChatMessage;
import com.konkuk.mafia.dto.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;




@Controller
@RequiredArgsConstructor
public class SocketController {

    private final SimpMessageSendingOperations messagingTemplate;

    //입장 알림
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    //마피아 채팅(밤)
    @MessageMapping("/chat.mafia")
    @SendTo("/topic/mafia")
    public ChatMessage sendMafiaMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        return chatMessage;
    }

    //단체 채팅(낮)
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    //사회자에게 채팅
    @MessageMapping("/chat.mc")
    @SendTo("/topic/mc")
    public ChatMessage sendMCMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }


    //역할 정해주기
    @MessageMapping("/role")
    @SendToUser("/start.role")
    public ChatMessage sendRole(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }


}