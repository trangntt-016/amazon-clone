import { Component, OnInit } from '@angular/core';
import { RegisterReq } from '../model/RegisterReq';
import { AuthService } from '../service/auth.service';
import { NgForm } from '@angular/forms';

enum Role { CUSTOMER, SELLER};


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  signUpUser: RegisterReq;
  OTPVerified: boolean;
  verifiedEmail: string;
  OTPCode:number;

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
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.signUpUser = new RegisterReq();
    this.OTPVerified = false;
  }

  onSubmit(f: NgForm): void{

    if(f.value.email.length >= 5 && f.value.email.length <= 30 && f.value.password.length >= 8){
      this.signUpUser.email = f.value.email;
      this.signUpUser.password = f.value.password;
      this.authService.register(this.signUpUser).subscribe(
         (userOTP) => {
           this.OTPVerified = true;
           this.verifiedEmail = userOTP.email;
        }
        ,(err) => {
           console.log(err);
        })
    }
  }

  onSubmitOTP(f: NgForm): void{
    console.log(f.value);
  }



}
