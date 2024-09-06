import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { OrderDTO } from '../dtos/order.dto';
import { Observable } from 'rxjs';
import { OrderResponse } from '../responses/order/OrderResponse';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  public apiOrder = `${environment.apiBaseUrl}/orders`;
  public apiGetAllOrders = `${this.apiOrder}/get-orders-by-keyword`;
  constructor(private http: HttpClient) {}
  createOrder(orderData: OrderDTO): Observable<OrderDTO> {
    return this.http.post<OrderDTO>(this.apiOrder, orderData);
  }
  getOrderById(id: Number): Observable<OrderResponse> {
    return this.http.get<OrderResponse>(this.apiOrder + `/${id}`);
  }
  getAllOrder(
    keyword: string,
    page: number,
    limit: number
  ): Observable<OrderResponse[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString())
      .set('keyword', keyword.toString());
    return this.http.get<OrderResponse[]>(this.apiGetAllOrders, { params });
  }
  updateOrder(id: number | any, orderDTO: OrderDTO): Observable<OrderResponse> {
    return this.http.put<OrderResponse>(`${this.apiOrder}/${id}`, orderDTO);
  }
  deleteOrder(id: number | any) {
    return this.http.delete(`${this.apiOrder}/${id}`, { responseType: 'text' });
  }
}
