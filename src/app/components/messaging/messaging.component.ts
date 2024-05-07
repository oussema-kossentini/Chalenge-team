import { Component } from '@angular/core';
import { Chat } from '../../models/chat.model';
import { Message } from '../../models/message.model';
import { ChatService } from '../service/chat.service';
import { Router } from '@angular/router';
import { Subject, interval, takeUntil } from 'rxjs';

@Component({
  selector: 'app-messaging',
  templateUrl: './messaging.component.html',
  styleUrl: './messaging.component.scss'
})
export class MessagingComponent {
  
  chatList: Chat[] = [];
  messageList: Message[] = [];
  selectedChatId?: number;
  newMessage: string = '';
  username: string = ''; // Déclarez la propriété username

  private unsubscribe$ = new Subject<void>();

  constructor(private chatService: ChatService, private router: Router) {}

  ngOnInit(): void {
    this.loadChats();
    this.setupChatUpdateInterval();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  loadChats(): void {
    this.chatService.getAllChats().subscribe(chats => {
      this.chatList = chats;
    });
  }

  loadMessages(chatId: number): void {
    this.selectedChatId = chatId;
    this.chatService.getChatById(chatId).subscribe(chat => {
      this.messageList = chat.messageList;
    });
  }

  sendMessage(): void {
    if (this.selectedChatId !== undefined) {
      const message: Message = {
        senderEmail: sessionStorage.getItem('username') ?? '',
        time: new Date(),
        replymessage: this.newMessage
      };

      this.chatService.addMessage(this.selectedChatId, message).subscribe(() => {
        this.loadMessages(this.selectedChatId!);
        this.newMessage = '';
      });
    }
  }

  createNewChat(secondUserName: string): void {
    const newChat: Chat = {
      firstUserName: sessionStorage.getItem('username') ?? '',
      secondUserName,
      messageList: []
    };

    this.chatService.createChat(newChat).subscribe(createdChat => {
      this.selectedChatId = createdChat.chatId!;
      this.router.navigate(['/messaging', this.selectedChatId]);
    });
  }

  logout(): void {
    // Déconnexion de l'utilisateur
  }

  private setupChatUpdateInterval(): void {
    interval(1000)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(() => {
        if (this.selectedChatId !== undefined) {
          this.chatService.getChatById(this.selectedChatId).subscribe(chat => {
            this.messageList = chat.messageList;
          });
        }
      });
  }

  goToChat(username: string): void {
    this.chatService.getChatByUsernames(sessionStorage.getItem('username') ?? '', username)
      .subscribe(
        (data: any) => {
          if (data && data.chatId !== undefined) {
            this.selectedChatId = data.chatId;
            if (this.selectedChatId !== undefined) {
              sessionStorage.setItem("chatId", this.selectedChatId.toString());
            }
          }
        },
        (error: any) => {
          if (error.status == 404) {
            const newChat: Chat = {
              firstUserName: sessionStorage.getItem('username') ?? '',
              secondUserName: username,
              messageList: []
            };
            this.chatService.createChat(newChat).subscribe(
              (data: any) => {
                if (data && data.chatId !== undefined) {
                  this.selectedChatId = data.chatId;
                  if (this.selectedChatId !== undefined) {
                    sessionStorage.setItem("chatId", this.selectedChatId.toString());
                  }
                }
              },
              (error: any) => {
                console.error("Erreur lors de la création du chat :", error);
              }
            );
          } else {
            console.error("Erreur lors de la récupération du chat :", error);
          }
        }
      );
  }
  
  firstUserName = sessionStorage.getItem('username');
  senderEmail = sessionStorage.getItem('username');
  senderCheck = sessionStorage.getItem('username');
  chatId: any = sessionStorage.getItem('chatId');
  color = "";
  secondUserName = "";
  loadChatByEmail(firstUserName: string, secondUserName: string): void {
    console.log(firstUserName, secondUserName);
    // Supprimer l'ancien chatId
    sessionStorage.removeItem("chatId");
  
    // Vérifier s'il existe une conversation entre les deux utilisateurs
    this.chatService.getChatByUsernames(firstUserName, secondUserName).subscribe(data => {
      // Si une conversation existe, récupérer son ID et le stocker dans sessionStorage
      if (data && data.chatId !== undefined) {
        this.chatId = data.chatId;
        console.log(this.chatId);
        sessionStorage.setItem('chatId', this.chatId);
  
        // Mettre à jour les messages de la conversation périodiquement
        setInterval(() => {
          this.chatService.getChatById(this.chatId).subscribe(chat => {
            this.messageList = chat.messageList;
            this.secondUserName = chat.secondUserName;
            this.firstUserName = chat.firstUserName;
          });
        }, 1000);
      }
    });
  }
  
  private startChatUpdateInterval(): void {
    interval(1000)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(() => {
        if (this.selectedChatId !== undefined) {
          this.chatService.getChatById(this.selectedChatId!).subscribe(data => {
            this.messageList = data.messageList;
          });
        }
      });
  }
}
