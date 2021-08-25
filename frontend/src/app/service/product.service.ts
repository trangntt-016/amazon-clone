import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductDto } from '../model/ProductDto';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  product: ProductDto;

  httpOptions: {headers: HttpHeaders} = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  pushFileToStorage(files: File[]): Observable<HttpEvent<{}>> {
    return null;
  }

  createAProduct(product: FormData): Observable<any>{
    return this.http.post<any>(`${environment.productAPI}`, product);
  }

  getProductsByCategoryIdKeyword(
    categoryId: number,
    keyword: string,
    pageIdx: number,
    perPage: number,
    brandIdStr: number[],
    priceStart: number,
    priceEnd: number,
    sortType: string): Observable<any>{
    const userId = this.authService.readToken().userId;

    let queryParams = '';
    if (categoryId !== 0 && ! isNaN(categoryId)){
      queryParams += 'categoryId=' + categoryId;
    }
    if (keyword !== null){
      if (queryParams !== '') queryParams += '&';
      queryParams += 'keyword=' + keyword;
    }
    if (pageIdx !== null){
      if (queryParams !== '') { queryParams += '&'; }
      queryParams += 'pageIdx=' + pageIdx;
    }
    if (perPage !== null){
      queryParams += '&perPage=' + perPage;
    }
    if (brandIdStr !== null){
      queryParams += '&brandIdStr=' + brandIdStr.toString();
    }
    if (!isNaN(priceStart) && !isNaN(priceEnd)){
      queryParams += '&priceStart=' + priceStart;
      queryParams += '&priceEnd=' + priceEnd;
    }
    if (sortType !== null && sortType !== undefined){
      queryParams += '&sortType=' + sortType;
    }
    if (userId !== null){
      queryParams += '&userId=' + userId;
    }
    else if(userId == null){
      localStorage.setItem("history_categoryId", categoryId.toString());
    }
    return this.http.get<any>(`${environment.productAPI}?${queryParams}`);
  }

  getProductByProductId(id: number): Observable<any>{
    return this.http.get<any>(`${environment.productAPI}/${id}`);
  }

}
