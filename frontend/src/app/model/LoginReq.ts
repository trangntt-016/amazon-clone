export class LoginReq {
  email: string;
  password: string;
  isRememberMe: boolean;
  constructor() {
    this.email = "";
    this.password = "";
    this.isRememberMe = false;
  }

}
