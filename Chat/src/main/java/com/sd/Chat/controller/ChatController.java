package com.sd.Chat.controller;


import com.sd.Chat.dto.MessageDTO;
import com.sd.Chat.dto.MessageReadDTO;
import com.sd.Chat.dto.TypingDTO;
import com.sd.Chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {


    private final ChatService chatService;

    @MessageMapping("/private/{receiverId}")
    public void sendPrivateMessage(@DestinationVariable String receiverId, @Payload MessageDTO messageDTO) {
        chatService.sendPrivateMessage(receiverId, messageDTO.getSenderId(), messageDTO.getMessage(), messageDTO.getMessageId());
    }

    @MessageMapping("/chat")
    public void sendPublicMessage(@Payload MessageDTO messageDTO) {
        chatService.sendPublicMessage(messageDTO.getSenderId(), messageDTO.getMessage(), messageDTO.getMessageId());
    }


    @MessageMapping("/read/{receiverId}")
    public void notifyMessageRead(@DestinationVariable String receiverId, @Payload MessageReadDTO messageReadDTO) {
        chatService.notifyMessageRead(receiverId, messageReadDTO.getMessageId());
    }

    @MessageMapping("/typing/{receiverId}")
    public void notifyTyping(@DestinationVariable String receiverId, @Payload TypingDTO typingDTO) {
        System.out.println("Typing notification received:");
        System.out.println("Receiver ID: " + receiverId);
        System.out.println("Sender ID: " + typingDTO.getSenderId());
        System.out.println("Is Typing: " + typingDTO.isTyping());
        chatService.notifyTyping(receiverId, typingDTO.getSenderId(), typingDTO.isTyping());
    }
}
