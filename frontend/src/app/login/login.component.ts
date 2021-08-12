import { Component, OnInit } from '@angular/core';
import { LoginReq } from '../model/LoginReq';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  loginUser: LoginReq;

  constructor() { }

  ngOnInit(): void {
    this.loginUser = new LoginReq();
  }
  onChange(event): void{
    this.loginUser.isRememberMe = event;
  }

}
