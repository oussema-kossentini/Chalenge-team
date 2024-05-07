import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from  '@angular/common/http';
import { Comment } from "./Comment";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class CommentService{
    private apiUrl = 'http://localhost:8080/api/comments';
    constructor(private http:HttpClient){}
    addCommentToPublication( publicationId:string, comment: Comment): Observable<any> {
        return this.http.post<any>(`${this.apiUrl}/${publicationId}/comments`, comment);
    }
    getCommentsForPublication(publicationId: string): Observable<Comment[]> {
        return this.http.get<Comment[]>(`${this.apiUrl}/${publicationId}`);
      }
      removeComment(commentId: string): Observable<any> {
        return this.http.delete(`${this.apiUrl}/remove-comments/${commentId}`);
      }
      modifyComment( comment: any): Observable<any> {
        return this.http.put<any>(`${this.apiUrl}/modify-Comment`, comment);
      }
      getPublicationById(id: string): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/get?publication-id=${id}`);
      }

  analyzeSentiment(sentence: string): Observable<any> {
    return this.http.post<any>('http://localhost:8080/api/comments/analyze-sentiment', { sentence });
  }

}
