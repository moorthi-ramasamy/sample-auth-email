import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of, tap } from 'rxjs';
import { Email } from './email/Email';

@Injectable({
  providedIn: 'root'
})
export class EmailnotifyService {
  console: any;

  constructor(
    private httpClient: HttpClient,
  ) { }
  
  public sendEmailWithAttachment(file: File, fromEmailAddress: string, toEmailAddress: string) {
    let uploadDate = formatDate(new Date(), 'yyyy/MM/dd', 'en');
    const payload = { 
      fromEmailAddress: fromEmailAddress, 
      toEmailAddress: toEmailAddress, 
      uploadUser: sessionStorage.getItem("currentUser"),
      uploadDate: uploadDate
    };

    let formParams = new FormData();
    formParams.append('request', JSON.stringify(payload));
    formParams.append('fileAttachment', file);
    return this.httpClient.post('http://localhost:8080/email/sendEmailWithAttachment', formParams)
  }

  public getAllSentEmails(): Observable<Email[]>{
    return this.httpClient.get<Email[]>('http://localhost:8080/email/getAllSentEmails').pipe(
      catchError(this.handleError<Email[]>('getAllSentEmails', []))
    );
  }
  
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }
}