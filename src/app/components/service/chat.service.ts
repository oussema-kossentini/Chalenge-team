import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Chat } from '../../models/chat.model';
import { HttpClient } from '@angular/common/http';
import { Message } from '../../models/message.model';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private baseUrl = 'http://localhost:8080'; // Assurez-vous de remplacer par l'URL de votre backend

  constructor(private http: HttpClient) { }

  // Ajoutez une méthode pour envoyer une requête POST pour créer un nouveau chat
  createChat(chat: Chat): Observable<Chat> {
    return this.http.post<Chat>(`${this.baseUrl}/chats/add`, chat);
  }

  // Ajoutez une méthode pour envoyer une requête GET pour récupérer tous les chats
  getAllChats(): Observable<Chat[]> {
    return this.http.get<Chat[]>(`${this.baseUrl}/chats/all`);
  }

  // Ajoutez une méthode pour envoyer une requête GET pour récupérer un chat par son ID
  getChatById(chatId: number): Observable<Chat> {
    return this.http.get<Chat>(`${this.baseUrl}/chats/${chatId}`);
  }
  getChatByUsernames(firstUsername: string, secondUsername: string): Observable<Chat> {
    return this.http.get<Chat>(`${this.baseUrl}/chats/getByUsernames/${firstUsername}/${secondUsername}`);
  }
  addMessage(chatId: number, message: Message): Observable<Chat> {
    return this.http.put<Chat>(`${this.baseUrl}/message/${chatId}`, message);
  }

}
