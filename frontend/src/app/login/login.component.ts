import { Component, OnInit } from '@angular/core';
import { UserAuthReq } from '../model/UserAuthReq';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginUser: UserAuthReq;

  constructor() { }

  ngOnInit(): void {
    this.loginUser = new UserAuthReq();
  }
  onChange(event){
    this.loginUser.isRememberMe = event;

  }

}
