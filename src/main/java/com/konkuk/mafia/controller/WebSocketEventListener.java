package com.konkuk.mafia.controller;

import com.konkuk.mafia.dto.ChatMessage;
import com.konkuk.mafia.dto.ChatMessage.MessageType;
import com.konkuk.mafia.dto.Roles;
import com.konkuk.mafia.dto.Users;
import com.konkuk.mafia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;

@Component
public class WebSocketEventListener {



    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    int num = 0;
    List<String> gamers = new ArrayList<>();
    Roles.ROLE[] roles = {Roles.ROLE.CITIZEN, Roles.ROLE.CITIZEN, Roles.ROLE.CITIZEN, Roles.ROLE.CITIZEN, Roles.ROLE.MAFIA, Roles.ROLE.MAFIA, Roles.ROLE.DOCTOR, Roles.ROLE.POLICE, Roles.ROLE.MC};
    HashMap<String, Roles.ROLE> roleHashMap = new HashMap<>();

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private UserService service;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        num = num+1;

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        System.out.println("Gamers: " + headerAccessor.getSessionId());
        gamers.add((String)headerAccessor.getSessionId());

        if(num == 9) {
            Collections.shuffle(Arrays.asList(roles));

            for(int i=0; i<9; i++) {
                roleHashMap.put(gamers.get(i), roles[i]);

                Roles r = new Roles();
                r.setRole(roles[i]);
                messagingTemplate.convertAndSendToUser(gamers.get(i), "/topic/public", r);
            }

            //System.out.println(roleHashMap.toString());

        }
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws Exception{
        num = num-1;
        System.out.println("NUM:" + num);
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        gamers.remove((String)headerAccessor.getSessionId());
        System.out.println(gamers.toString());
        String username = (String) headerAccessor.getSessionAttributes().get("username");


        if(username != null) {
            service.setUserStateFalse(username);
            logger.info("User Disconnected : " + username);

//            ChatMessage chatMessage = new ChatMessage();
//            chatMessage.setType(MessageType.LEAVE);
//            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public",  service.getUserList());
        }
    }
}