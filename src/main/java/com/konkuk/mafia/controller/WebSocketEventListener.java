//package com.konkuk.mafia.controller;
//
//import com.konkuk.mafia.dto.ChatMessage;
//import com.konkuk.mafia.dto.ChatMessage.MessageType;
//import com.konkuk.mafia.dto.Roles;
//import com.konkuk.mafia.dto.Users;
//import com.konkuk.mafia.service.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.util.*;
//
//@Component
//public class WebSocketEventListener {
//
//    @Autowired
//    private SimpMessageSendingOperations messagingTemplate;
//
//    @Autowired
//    private UserService service;
//
//    private Listener listener;
//
////    @EventListener
////    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
////        num = num+1;
////
////        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
////
////        //System.out.println("Gamers: " + headerAccessor.getSessionId());
////        gamers.add((String)headerAccessor.getSessionId());
////
////        if(num == 9) {
////            Collections.shuffle(Arrays.asList(roles));
////
////            for(int i=0; i<9; i++) {
////                roleHashMap.put(gamers.get(i), roles[i]);
////
////                Roles r = new Roles();
////                r.setRole(roles[i]);
////                messagingTemplate.convertAndSendToUser(gamers.get(i), "/topic/public", r);
////            }
////
////            //System.out.println(roleHashMap.toString());
////
////        }
////        logger.info("Received a new web socket connection");
////    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws Exception{
//
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        //System.out.println(username);
//
//        if(username != null) {
//            service.setUserStateFalse(username);
//
////            ChatMessage chatMessage = new ChatMessage();
////            chatMessage.setType(MessageType.LEAVE);
////            chatMessage.setSender(username);
//
//            messagingTemplate.convertAndSend("/topic/public",  service.getUserList());
//        }
//    }
//
//}