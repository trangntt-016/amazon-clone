import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RegisterReq } from '../model/RegisterReq';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  httpOptions: {headers: HttpHeaders} = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  constructor(
    private http: HttpClient
  ) { }

  register(registerUser: RegisterReq): Observable<any>{
    return this.http.post<any>(`${environment.userAPI}/register`, registerUser, this.httpOptions);
  }
}
