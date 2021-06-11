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

    private HashMap<String, String> userRoleList = new HashMap<>();
    private String randomWord = null;

    //입장 알림
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public/start")
    public String addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception{
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        service.setUserStateTrue(chatMessage.getSender());
        System.out.println("GET NICK NAME: " + service.getUser(chatMessage.getSender()).getNickName());
        return service.getUser(chatMessage.getSender()).getNickName();
    }

    //직업 분배
    @MessageMapping("/role")
    @SendToUser("/queue/role")
    public String setRole(@Payload String userId) {
        Random random = new Random();

        int idx = random.nextInt(jobs.size()-1);

        String randomJob = jobs.get(idx);
        jobs.remove(idx);
        userRoleList.put(userId, randomJob);


        if(randomWord == null) {
            this.randomWord = words.get(random.nextInt(words.size()-1));
        }
        System.out.println("RANDOM WORD: " + randomWord);
        System.out.println("RANDOM JOB: " + randomJob);
        RoleMessage roleMessage = new RoleMessage();
        roleMessage.setRole(randomJob);

        if(randomJob.equals("마피아") == false) {
           roleMessage.setWord(randomWord);
        }
        return roleMessage.toString();
    }

    //사회자가 마피아 정보를 요청
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


    //단체 채팅
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

        //누군가 나가면 '남은 직업' 배열에 그 사람의 직업을 추가함.
        jobs.add(userRoleList.get(username));

        //모두가 나가서 '남은 직업' 배열이 8개가 되면 랜덤 단어를 비움.
        if(jobs.size()==8) {
            this.randomWord = null;
            System.out.println("RANDOM WORLD NULL");
        }

        //StompHeaderAccessor 에 저장해 놓은 세션 유저가 disconnected 되면 상태를 0으로 바꿈.
        if(username != null) {
            service.setUserStateFalse(username);
            messagingTemplate.convertAndSend("/topic/public",  service.getUserList());
        }
    }
}