import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, numberAttribute } from '@angular/core';
import { environment } from '../environments/environment';
import { Product } from '../models/product';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiGetAllProductsUrl = `${environment.apiBaseUrl}/products`;
  constructor(private http: HttpClient) {}
  getProducts(
    keyword: string,
    categoryId: number,
    page: number,
    limit: number
  ): Observable<Product[]> {
    const params = new HttpParams()
      .set('keyword', keyword.toString())
      .set('category_id', categoryId.toString())
      .set('page', page.toString())
      .set('limit', limit.toString());
    return this.http.get<Product[]>(this.apiGetAllProductsUrl, { params });
  }
  getDetailProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiGetAllProductsUrl}/${id}`);
  }
  getProductByIds(productIds: number[]): Observable<Product[]> {
    const params = new HttpParams().set('ids', productIds.join(','));
    return this.http.get<Product[]>(
      `${environment.apiBaseUrl}/products/by-ids`,
      { params }
    );
  }
}
