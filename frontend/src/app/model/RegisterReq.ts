export class RegisterReq {
  name: string;
  email: string;
  password: string;
  role: string;
  constructor() {
    this.name = '';
    this.email = '';
    this.password = '';
    this.role = '';
  }
}
