import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../models/Category';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private apiGetAllCategories = `${environment.apiBaseUrl}/categories`;
  constructor(private http: HttpClient) {}
  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.apiGetAllCategories);
  }
}
