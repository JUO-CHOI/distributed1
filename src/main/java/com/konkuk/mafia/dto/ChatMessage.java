package com.konkuk.mafia.dto;//package com.konkuk.mafia.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//import java.util.Date;
//
//@Data
//@AllArgsConstructor
//public class ChatMessage {
//
//    public enum MessageType {
//        ENTER, TALK
//    }
//    private MessageType type;
//    private int messageId;
//    private String userName;
//    private String message;
//    private Date sendTime;
//    private int roomId;
//}

import java.awt.*;

public class ChatMessage {

    //private MessageType type;
    private String content;
    private String sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}