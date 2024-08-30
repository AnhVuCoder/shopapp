import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { CartService } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { environment } from '../../environments/environment';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderResponse } from '../../responses/order/OrderResponse';
import { OrderService } from '../../services/order.service';
import { iterator } from 'rxjs/internal/symbol/iterator';

@Component({
  selector: 'app-order-detail',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, CommonModule, FormsModule],
  templateUrl: './order.detail.component.html',
  styleUrl: './order.detail.component.scss',
})
export class OrderDetailComponent implements OnInit {
  public orderResponse: OrderResponse = {
    id: 0,
    user_id: 0,
    fullname: '',
    phone_number: '',
    email: '',
    address: '',
    note: '',
    order_date: new Date(),
    status: '',
    total_money: 0,
    shipping_method: '',
    shipping_address: '',
    shipping_date: new Date(),
    payment_method: '',
    order_details: [],
  };
  constructor(private orderService: OrderService) {}
  ngOnInit(): void {
    this.getOrderDetails();
  }
  getOrderDetails(): void {
    const orderId = 4;
    this.orderService.getOrderById(orderId).subscribe({
      next: (response: OrderResponse) => {
        console.log(response);
        this.orderResponse.id = response.id;
        this.orderResponse.user_id = response.user_id;
        this.orderResponse.fullname = response.fullname;
        this.orderResponse.phone_number = response.phone_number;
        this.orderResponse.email = response.email;
        this.orderResponse.address = response.address;
        this.orderResponse.note = response.note;
        this.orderResponse.order_date = new Date(
          response.order_date[0],
          response.order_date[1] - 1,
          response.order_date[2]
        );
        this.orderResponse.status = response.status;
        this.orderResponse.total_money = response.total_money;
        this.orderResponse.shipping_method = response.shipping_method;
        this.orderResponse.shipping_date = new Date(
          response.shipping_date[0],
          response.shipping_date[1] - 1,
          response.shipping_date[2]
        );
        this.orderResponse.payment_method = response.payment_method;
        this.orderResponse.order_details = response.order_details.map(
          (item) => {
            item.product.thumbnail = `${environment}/products/images/${item.product.thumbnail}`;
            return item;
          }
        );
        let sum = 0;
        response.order_details.forEach((item) => {
          sum += item.price * item.number_of_products;
        });
        this.orderResponse.total_money = sum;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
