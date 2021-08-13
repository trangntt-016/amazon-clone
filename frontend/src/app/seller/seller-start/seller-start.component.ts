import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-seller-start',
  templateUrl: './seller-start.component.html',
  styleUrls: ['./seller-start.component.css']
})
export class SellerStartComponent implements OnInit {
  sellername: string;
  constructor(
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.sellername = this.authService.readToken().name;
  }

}
