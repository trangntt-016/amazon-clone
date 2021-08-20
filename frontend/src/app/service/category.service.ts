import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  httpOptions: {headers: HttpHeaders} = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(
    private http: HttpClient
  ) { }

  getRootCategories(): Observable<any>{
    return this.http.get<any>(`${environment.categoryAPI}/root`);
  }

  getRootCategoriesForSearch(): Observable<any>{
    return this.http.get<any>(`${environment.categoryAPI}/root-search`);
  }

  getBrandsFromCategoryId(categoryId: number): Observable<any>{
    return this.http.get<any>(`${environment.categoryAPI}/${categoryId}/brands`);
  }
}
