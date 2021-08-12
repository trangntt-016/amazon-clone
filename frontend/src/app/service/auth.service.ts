import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RegisterReq } from '../model/RegisterReq';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { VerifyOTPReq } from '../model/VerifyOTPReq';
import { RegisterOTPRes } from '../model/RegisterOTPRes';
import { LoginRes } from '../model/LoginRes';

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

  register(registerUser: RegisterReq): Observable<RegisterOTPRes>{
    return this.http.post<any>(`${environment.userAPI}/register`, registerUser, this.httpOptions);
  }

  verifyOTP(verifyOTPReqDto: VerifyOTPReq): Observable<any>{
    return this.http.post<any>(`${environment.userAPI}/verify-otp`, verifyOTPReqDto,  { observe: 'response' });
  }
}
