import { Injectable } from '@angular/core';
import {Client} from "@stomp/stompjs";
import {Observable, Subject} from "rxjs";
import {MessageDTO} from "../../../../shared/dto/messageDTO.model";
import {ReadDTO} from "../../../../shared/dto/readDTO.model";
import {TypingDTO} from "../../../../shared/dto/typingDTO.model";

@Injectable({
  providedIn: 'root'
})
export class WebsocketChatService {
  private userId: string | null = null;
  private stompClientChat: Client | null = null;
  private chatSubject: Subject<MessageDTO> = new Subject<MessageDTO>();
  private privateMessageSubject:  Subject<MessageDTO> = new Subject<MessageDTO>();
  private messageReadSubject: Subject<ReadDTO> = new Subject<ReadDTO>();
  private typingSubject: Subject<TypingDTO> = new Subject<TypingDTO>();

  constructor() {
    this.initializeUserId();
    this.connectToChat();
  }

  private initializeUserId() {
    const loggedUser = JSON.parse(sessionStorage.getItem('loggedUser') || '{}');
    this.userId = loggedUser.idUser;
  }

  private connectToChat() {
    const socketUrl = 'ws://chat.localhost/api/ws';
    //const socketUrl = 'ws://localhost:8547/api/ws';
    this.stompClientChat = new Client({ brokerURL: socketUrl });

    this.stompClientChat = new Client({
      brokerURL: socketUrl,
      debug: (msg: string) => { console.log(msg); },
      onConnect: (frame: any) => {
        console.log('Connected to Chat MS: ' + frame);
        this.stompClientChat?.subscribe('/chat', (message) => {
          console.log('Public message:', message.body);
          this.chatSubject.next(JSON.parse(message.body));
        });
        this.stompClientChat?.subscribe(`/private/${this.userId}`, (message) => {
          console.log('Private message: ', message.body);
          this.privateMessageSubject.next(JSON.parse(message.body));
        });
        this.stompClientChat?.subscribe(`/read/${this.userId}`, (message) => {
          console.log('Message read confirmation:', message.body);
          this.messageReadSubject.next(JSON.parse(message.body));
        });
        this.stompClientChat?.subscribe(`/typing/${this.userId}`, (message) => {
          console.log('Typing confirmation:', message.body);
          this.typingSubject.next(JSON.parse(message.body));
        });
      },
      onStompError: (frame: any) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      }
    });

    this.stompClientChat.activate();
  }

  public getPublicMessages(): Observable<MessageDTO> {
    return this.chatSubject.asObservable();
  }

  public getPrivateMessages(): Observable<MessageDTO> {
    return this.privateMessageSubject.asObservable();
  }

  public getMessageReadStatus(): Observable<ReadDTO> {
    return this.messageReadSubject.asObservable();
  }

  public getTypingStatus(): Observable<TypingDTO> {
    return this.typingSubject.asObservable();
  }

  public sendPublicMessage(message: string): void {
    if (this.stompClientChat && this.stompClientChat.connected) {
      const messageDTO: MessageDTO = {
        senderId: this.userId!,
        message: message
      };

      this.stompClientChat.publish({
        destination: '/app/chat',
        body: JSON.stringify(messageDTO),
      });
    }
  }

  public sendPrivateMessage(receiverId: string, message: string, messageId: string): void {
    if (this.stompClientChat && this.stompClientChat.connected) {
      const messageDTO: MessageDTO = {
        senderId: this.userId!,
        message: message,
        messageId: messageId
      };

      this.stompClientChat.publish({
        destination: `/app/private/${receiverId}`,
        body: JSON.stringify(messageDTO),
      });
    }
  }

  public sendTypingNotification(receiverId: string, senderId: string, isTyping: boolean): void {
    if (this.stompClientChat && this.stompClientChat.connected) {
      const typingDTO: TypingDTO = {
        senderId: senderId,
        typing: isTyping
      };

      this.stompClientChat.publish({
        destination: `/app/typing/${receiverId}`,
        body: JSON.stringify(typingDTO),
      });
      console.log('Sending typing notification JSON:', JSON.stringify(typingDTO));

    }
  }

  public sendReadMessageNotification(receiverId: string, messageId: string): void {
    if (this.stompClientChat && this.stompClientChat.connected) {
      const readDTO: ReadDTO = {
        messageId: messageId
      };

      this.stompClientChat.publish({
        destination: `/app/read/${receiverId}`,
        body: JSON.stringify(readDTO),
      });
    }
  }

}
