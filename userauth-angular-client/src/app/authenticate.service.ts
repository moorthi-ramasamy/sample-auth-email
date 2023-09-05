import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { User } from './login/User';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  constructor(private http: HttpClient) { }

  public authenticate(userName: string, password: string) : Observable<User>{
    const payload = { userName: userName, password: btoa(password) };
    return this.http
      .post<User>("http://localhost:8080/userauth/authenticate", payload);
  }

}
