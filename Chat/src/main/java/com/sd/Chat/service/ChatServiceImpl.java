package com.sd.Chat.service;

import com.sd.Chat.dto.MessageDTO;
import com.sd.Chat.dto.MessageReadDTO;
import com.sd.Chat.dto.TypingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Override
    public void sendPrivateMessage(String receiverId, String senderId, String message, String messageId) {
        MessageDTO messageDTO = new MessageDTO(senderId, message, messageId);
        String destination = "/private/" + receiverId;
        messagingTemplate.convertAndSend(destination, messageDTO);
    }

    @Override
    public void sendPublicMessage(String senderId, String message, String messageId) {
        MessageDTO messageDTO = new MessageDTO(senderId, message, messageId);
        messagingTemplate.convertAndSend("/chat", messageDTO);
    }

    @Override
    public void notifyMessageRead(String receiverId, String messageId) {
        MessageReadDTO messageReadDTO = new MessageReadDTO(messageId);
        String destination = "/read/" + receiverId;
        messagingTemplate.convertAndSend(destination, messageReadDTO);
    }

    @Override
    public void notifyTyping(String receiverId, String senderId, boolean isTyping) {
        TypingDTO typingDTO = new TypingDTO(senderId, isTyping);
        String destination = "/typing/" + receiverId;
        messagingTemplate.convertAndSend(destination, typingDTO);
    }
}
