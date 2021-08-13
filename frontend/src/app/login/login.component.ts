import { Component, OnInit } from '@angular/core';
import { LoginReq } from '../model/LoginReq';
import { NgForm } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  loginUser: LoginReq;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loginUser = new LoginReq();
  }

  onChange(event): void{
    this.loginUser.isRememberMe = event;
  }

  onSubmit(f: NgForm): void{
    this.authService.logIn(this.loginUser).subscribe((success) => {
      const jwt = success.headers.get('Authorization').substring('Bearer '.length);

      if (success.body.isRememberMe){
        localStorage.setItem('access_token', jwt);
      }
      else{
        sessionStorage.setItem('access_token', jwt);
      }

      if(this.authService.readToken().role == "CUSTOMER"){
        this.router.navigate(['/']);
      }
      else{
        this.router.navigate(['seller/start']);
      }
    })
  }

}
