import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RegisterReq } from '../model/RegisterReq';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { VerifyOTPReq } from '../model/VerifyOTPReq';
import { RegisterOTPRes } from '../model/RegisterOTPRes';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Payload } from '../model/Payload';
import { LoginReq } from '../model/LoginReq';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private helper = new JwtHelperService();

  httpOptions: {headers: HttpHeaders} = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  constructor(
    private http: HttpClient
  ) { }

  register(registerUser: RegisterReq): Observable<RegisterOTPRes>{
    return this.http.post<any>(`${environment.userAPI}/register`, registerUser, this.httpOptions);
  }

  verifyOTP(verifyOTPReqDto: VerifyOTPReq): Observable<any>{
    return this.http.post<any>(`${environment.userAPI}/verify-otp`, verifyOTPReqDto,  { observe: 'response' });
  }

  logIn(loginReq: LoginReq): Observable<any> {
    return this.http.post<any>(`${environment.userAPI}/login`, loginReq,  { observe: 'response' });
  }
  getToken(): string{
    if (localStorage.getItem('access_token')){
      return localStorage.getItem('access_token');
    }
    return sessionStorage.getItem('access_token');
  }

  readToken(): Payload{
    const rawToken = this.getToken();
    return this.helper.decodeToken(rawToken);
  }

  isAuthenticated(): boolean{
    if (this.getToken()){
      return true;
    }
    return false;
  }

  getRole(): string{
    return this.readToken().role;
  }
}
