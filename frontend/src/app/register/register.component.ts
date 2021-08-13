import { Component, OnInit } from '@angular/core';
import { RegisterReq } from '../model/RegisterReq';
import { AuthService } from '../service/auth.service';
import { NgForm } from '@angular/forms';
import { VerifyOTPReq } from '../model/VerifyOTPReq';
import { Router } from '@angular/router';

enum Role { CUSTOMER, SELLER};


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  signUpUser: RegisterReq;
  verifyOTPUser: VerifyOTPReq;

  isOTPVerified: boolean;
  verifiedEmail: string;
  OTPCode: Number;

  check_box_type = Role;

  currentlyChecked: Role;

  selectCheckBox(targetType: Role) {
    if(this.currentlyChecked === targetType) {
      this.currentlyChecked = Role.CUSTOMER;
      return;
    }
    this.currentlyChecked = targetType;

    switch (this.currentlyChecked) {
      case 0:
        this.signUpUser.role = 'CUSTOMER';
        break;
      case 1:
        this.signUpUser.role = 'SELLER';
        break;
    }

  }

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.signUpUser = new RegisterReq();
    this.isOTPVerified = false;
    this.verifyOTPUser = new VerifyOTPReq();
  }

  onSubmit(f: NgForm): void{
    if(f.value.email.length >= 5 && f.value.email.length <= 30 && f.value.password.length >= 8){
      this.signUpUser.email = f.value.email;
      this.signUpUser.password = f.value.password;
      this.authService.register(this.signUpUser).subscribe(
         (userOTP) => {
           this.isOTPVerified = true;
           this.verifiedEmail = userOTP.email;
           this.verifyOTPUser.userId = userOTP.userId;
        }
        ,(err) => {
           console.log(err);
        })
    }
  }

  onSubmitOTP(f: NgForm): void{
    if(this.OTPCode != null ){
      this.verifyOTPUser.otpCode = this.OTPCode.toString();
      this.authService.verifyOTP(this.verifyOTPUser).subscribe(
        (success) => {
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

        }
      )
    }
  }



}
