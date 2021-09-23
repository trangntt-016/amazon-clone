import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { CartItemDto } from '../model/CartItemDto';

@Injectable({
  providedIn: 'root'
})
export class CartItemService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  addProductToCart(item: CartItemDto): Observable<any> {
      return this.http.post<any>(`${environment.cartAPI}/add`, item);
  }
}
