package com.konkuk.mafia.controller;


import com.google.gson.JsonObject;
import com.konkuk.mafia.dto.ChatMessage;
import com.konkuk.mafia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


@Controller
@RequiredArgsConstructor
@Component
public class SocketController {

    @Autowired
    private UserService service;

    private final SimpMessageSendingOperations messagingTemplate;
    //private List<Users> usersList;
    private List<String> jobs = new ArrayList<String>() {
        {
            add("Mafia");
            add("Citizen");
            add("Mafia");
            add("Citizen");
            add("Police");
            add("Citizen");
            add("Doctor");
            add("Citizen");
            add("MC");
        }
    };

    private HashMap<String, String> userRoleList = new HashMap<>();

    //입장 알림
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public/start")
    public String addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception{
        service.setUserStateTrue(chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        JsonObject object = new JsonObject();
        //object.addProperty("nickname", service.getUser(chatMessage.getSender()).getNickName());
        //return object.toString();
        return service.getUser(chatMessage.getSender()).getNickName();
    }

    //직업 분배
    @MessageMapping("/role")
    @SendToUser
    public String setRole(@Payload String userId) {
        System.out.println("JOB ARR BEFORE SIZE: " + jobs.size());
        Random random = new Random();
        int idx = random.nextInt(jobs.size()-1);

        String randomJob = jobs.get(idx);
        jobs.remove(idx);
        userRoleList.put(userId, randomJob);

        System.out.println("JOB ARR SIZE: " + jobs.size());

        return randomJob;
    }


    //단체 채팅(낮)
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public/day")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    //마피아+사회자 채팅(밤)
    @MessageMapping("/chat.mafia")
    @SendTo("/topic/mafia")
    public ChatMessage sendMafiaMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        return chatMessage;
    }

    //의사+사회자 채팅(낮)
    @MessageMapping("/chat.doctor")
    @SendTo("/topic/doctor")
    public ChatMessage sendDoctorMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    //경찰+사회자 채팅(낮)
    @MessageMapping("/chat.police")
    @SendTo("/topic/police")
    public ChatMessage sendPoliceMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    //낮밤 알림
    @MessageMapping("/chat.dayNight")
    @SendTo("/topic/dayNight")
    public String sendDayNightMessage(@Payload String now) {

        String str;
        if(now.equals("night")) {
            str = "day";
        } else {
            str = "night";
        }

        return str;
    }

    //죽은 사람 알림
    @MessageMapping("/chat.died")
    @SendTo("/topic/died")
    public String sendDeadPersonMessage(@Payload String userId) {
        return userId;
    }

    //방에서 나감. 또는 로그 아웃.
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws Exception{

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        //System.out.println(username);
        jobs.add(userRoleList.get(username));


        if(username != null) {
            service.setUserStateFalse(username);

//            ChatMessage chatMessage = new ChatMessage();
//            chatMessage.setType(MessageType.LEAVE);
//            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public",  service.getUserList());
        }
    }

}