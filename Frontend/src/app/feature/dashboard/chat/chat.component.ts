import {Component, OnInit} from '@angular/core';
import {UserModel} from "../../../shared/models/user.model";
import {UserService} from "../../../core/service/user/user.service";
import {MessageDTO} from '../../../shared/dto/messageDTO.model';
import {WebsocketChatService} from "../../../core/service/websocket/chat/websocket-chat.service";
import {ReadDTO} from "../../../shared/dto/readDTO.model";
import {TypingDTO} from "../../../shared/dto/typingDTO.model";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent implements OnInit {

  users: UserModel[] = [];
  receiverId: string = '';
  messages: MessageDTO[] = [];
  message: string = '';
  loggedInUserId: string = '';
  senderName: string = '';
  lastMessageId: string = '';
  typingNotification: string = '';
  private typingTimer: any;
  private typingTimeout = 2500;

  constructor(
    private userService: UserService,
    private websocketChatService: WebsocketChatService
  ) {
  }

  ngOnInit(): void {
    this.loggedInUserId = sessionStorage.getItem('loggedUser') ? JSON.parse(sessionStorage.getItem('loggedUser') || '{}').idUser : '';
    this.loadUsers();
    this.websocketChatService.getPublicMessages().subscribe((message: MessageDTO) => {
      this.senderName = this.getUserNameById(message.senderId);
      if(message.senderId !== this.loggedInUserId) {
        this.messages.push(message);
      }
    });

    this.websocketChatService.getPrivateMessages().subscribe((message: MessageDTO) => {
      if (this.receiverId === message.senderId) {
        this.senderName = this.getUserNameById(message.senderId);
        this.lastMessageId = (message.messageId)!;
        message.read = false;
        this.messages.push(message);
      }
    });

    this.websocketChatService.getMessageReadStatus().subscribe((readDTO: ReadDTO) => {
      this.markMessageAsRead(readDTO.messageId);
    });

    this.websocketChatService.getTypingStatus().subscribe((typingData: TypingDTO) => {
      this.showTypingNotification(typingData.senderId, typingData.typing);
    });

  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe(users => {
      this.users = users.filter(user => user.idUser !== this.loggedInUserId);
    });
  }



  sendMessage() {
    const uniqueId = crypto.randomUUID();
    if (this.receiverId === 'all') {
      this.websocketChatService.sendPublicMessage(this.message);
    } else {
      this.websocketChatService.sendPrivateMessage(this.receiverId, this.message, uniqueId);
    }
    const newMessage: MessageDTO = {
      senderId: this.loggedInUserId,
      message: this.message,
      read: false,
      messageId: uniqueId
    };

    this.messages.push(newMessage);
    this.message = '';
  }

  selectReceiver(user: string) {
    this.messages = [];
    this.receiverId = user;
  }

  getUserNameById(userId: string): string {
    const user = this.users.find(u => u.idUser === userId);
    return user ? user.name : '?';
  }

  onMessageBoxForTyping(): void {
    if (this.receiverId) {
      this.websocketChatService.sendTypingNotification(this.receiverId, this.loggedInUserId, true);
      console.log('Typing notification sent: true');

      clearTimeout(this.typingTimer);
      this.typingTimer = setTimeout(() => {
         this.websocketChatService.sendTypingNotification(this.receiverId, this.loggedInUserId, false);
      }, this.typingTimeout);
    }
  }

  onMessageBoxForSeen(): void {
    if (this.receiverId && this.lastMessageId) {
      this.websocketChatService.sendReadMessageNotification(this.receiverId, this.lastMessageId);
    }
  }


  markMessageAsRead(messageId: string): void {
    console.log("Marking message as read:", messageId);
    console.log("Current message list:", this.messages);


    const message = this.messages.find(msg => msg.messageId === messageId);

    if (message) {
      for (let i = 0; i < this.messages.length; i++) {
        this.messages[i].read = false;
      }
      message.read = true; // Setează statusul ca "citit"
      console.log("Message updated to read:", message);
    } else {
      console.warn("Message not found with ID:", messageId);
    }
  }

  isAdmin(): boolean {
    const loggedUser = JSON.parse(sessionStorage.getItem('loggedUser') || '{}');
    return loggedUser.role === 'ADMIN';
  }

  showTypingNotification(senderId: string, isTyping: boolean): void {
    const user = this.getUserNameById(senderId);
    if (isTyping) {
      this.typingNotification = `${user} is typing...`;
    } else {
      this.typingNotification = '';
    }
  }

}
