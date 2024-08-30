import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { OrderDTO } from '../dtos/order.dto';
import { Observable } from 'rxjs';
import { OrderResponse } from '../responses/order/OrderResponse';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  public apiOrder = `${environment.apiBaseUrl}/orders`;
  constructor(private http: HttpClient) {}
  createOrder(orderData: OrderDTO): Observable<OrderDTO> {
    return this.http.post<OrderDTO>(this.apiOrder, orderData);
  }
  getOrderById(id: Number): Observable<OrderResponse> {
    return this.http.get<OrderResponse>(this.apiOrder + `/${id}`);
  }
}
