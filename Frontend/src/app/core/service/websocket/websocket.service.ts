import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private stompClient: Client | null = null;
  private notificationSubject: Subject<string> = new Subject<string>();
  private userId: string | null = null;

  constructor() {
    this.initializeUserId();
    this.connect();
  }

  private initializeUserId() {
    const loggedUser = JSON.parse(sessionStorage.getItem('loggedUser') || '{}');
    this.userId = loggedUser.idUser;
  }

  private connect() {
    const socketUrl = 'ws://monitoring.localhost/api/ws';
    this.stompClient = new Client({ brokerURL: socketUrl });

    this.stompClient = new Client({
      brokerURL: socketUrl,
      debug: (msg: string) => { console.log(msg); },
      onConnect: (frame: any) => {
        console.log('Connected: ' + frame);
        this.stompClient?.subscribe(`/user/${this.userId}/topic/notifications`, (message) => {
          console.log('Received message:', message.body);
          this.notificationSubject.next(message.body);
        });
      },
      onStompError: (frame: any) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      }
    });

    this.stompClient.activate();
  }

  public getNotifications(): Observable<string> {
    return this.notificationSubject.asObservable();
  }
}
