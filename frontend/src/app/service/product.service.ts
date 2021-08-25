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
    return this.http.get<any>(`${environment.productAPI}?${queryParams}`);
  }

  getProductByProductId(id: number): Observable<any>{
    const userId = (this.authService.readToken()) ? this.authService.readToken().userId : '';

    const urlStr = `${environment.productAPI}/${id}?userId=${userId}`;

    return this.http.get<any>(urlStr);
  }

  getBrowsingHistoryProducts(categoryId): Observable<any>{
    const userId = (this.authService.readToken()) ? this.authService.readToken().userId : '';

    return this.http.get<any>(`${environment.productAPI}/history?userId=${userId}&categoryId=${categoryId}`);
  }

}
