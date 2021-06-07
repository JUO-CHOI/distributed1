package com.konkuk.mafia.controller;


import com.google.gson.JsonObject;
import com.konkuk.mafia.dto.ChatMessage;
import com.konkuk.mafia.dto.Roles;
import com.konkuk.mafia.dto.Users;
import com.konkuk.mafia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;


import java.util.List;


@Controller
@RequiredArgsConstructor
public class SocketController {

    @Autowired
    private UserService service;

    private final SimpMessageSendingOperations messagingTemplate;
    //private List<Users> usersList;


    //입장 알림
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public String addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception{
        //usersList = service.getUserList();
        service.setUserStateTrue(chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        List<Users> list = service.getUserList();
//        System.out.println(list.toString());
        JsonObject object = new JsonObject();
        object.addProperty("status", "succeed");
        return object.toString();
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