package com.sd.Chat.service;

public interface ChatService {
    void sendPrivateMessage(String receiverId, String senderId, String message, String messageId);
    void sendPublicMessage(String senderId, String message, String messageId);
    void notifyMessageRead(String receiverId, String messageId);
    void notifyTyping(String receiverId, String senderId, boolean isTyping);
}
