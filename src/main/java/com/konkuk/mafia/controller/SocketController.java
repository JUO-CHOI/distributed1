package com.konkuk.mafia.controller;


import com.google.gson.JsonObject;
import com.konkuk.mafia.dto.ChatMessage;
import com.konkuk.mafia.dto.RoleMessage;
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
            add("사회자");
            add("마피아");
            add("스파이");
            add("시민");
            add("시민");
            add("시민");
            add("시민");
            add("시민");
        }
    };

    private List<String> words = new ArrayList<String>() {
        {
            add("코끼리");
            add("에어컨");
            add("반지");
            add("해바라기");
            add("마우스");
            add("비행기");
            add("선풍기");
            add("양말");
            add("아이폰");
            add("크로플");
            add("떡볶이");
        }
    };

    private String selectedWord;
    private HashMap<String, String> userRoleList = new HashMap<>();

    Random random = new Random();
    String randomWord = null;

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
    @SendToUser("/queue/role")
    public String setRole(@Payload String userId) {
        //System.out.println("JOB ARR BEFORE SIZE: " + jobs.size());
        Random random = new Random();
        int idx = random.nextInt(jobs.size()-1);

        String randomJob = jobs.get(idx);
        jobs.remove(idx);
        userRoleList.put(userId, randomJob);
        System.out.println("RANDOM WORD: " + randomWord);

        if(randomWord == null) {
            this.randomWord = words.get(random.nextInt(words.size()-1));
        }

        RoleMessage roleMessage = new RoleMessage();
        roleMessage.setRole(randomJob);
        if(randomJob.equals("마피아") == false) {
           roleMessage.setWord(randomWord);
        }
        return roleMessage.toString();
    }

    //사회자가 마피아 요청
    @MessageMapping("/mafia")
    @SendToUser("/queue/mafia")
    public String getMafiaInfo() {
        for (String key : userRoleList.keySet()) {
            if (userRoleList.get(key).equals("마피아")) {
                System.out.println("MAFIA: " + key);
                return key;
            }
        }
        return null;
    }


    //단체 채팅(낮)
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public/day")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }


    //방에서 나감. 또는 로그 아웃.
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws Exception{

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        jobs.add(userRoleList.get(username));


        if(username != null) {
            service.setUserStateFalse(username);

            messagingTemplate.convertAndSend("/topic/public",  service.getUserList());
        }
    }

}