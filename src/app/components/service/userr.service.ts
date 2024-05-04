import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../models/user.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserrService {

  private baseUrl = 'http://localhost:8080'; // Assurez-vous de remplacer par l'URL de votre backend

  constructor(private http: HttpClient) { }

  // Ajoutez une méthode pour envoyer une requête POST pour créer un nouvel utilisateur
  addUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/user/add`, user);
  }

  // Ajoutez une méthode pour envoyer une requête GET pour récupérer tous les utilisateurs
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/user/getall`);
  }

  // Ajoutez une méthode pour envoyer une requête GET pour récupérer un utilisateur par son nom d'utilisateur
  getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/user/getbyusername/${username}`);
  }
}
