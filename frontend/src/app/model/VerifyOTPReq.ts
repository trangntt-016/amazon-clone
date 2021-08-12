export class VerifyOTPReq {
  userId: string;
  otpCode: string;
  constructor() {
    this.userId = '';
    this.otpCode = '';
  }
}
