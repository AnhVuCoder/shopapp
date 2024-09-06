import { Component, OnInit } from '@angular/core';
import { OrderResponse } from '../../../responses/order/OrderResponse';
import { OrderService } from '../../../services/order.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { clearAppScopedEarlyEventContract } from '@angular/core/primitives/event-dispatch';
import { environment } from '../../../environments/environment';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderDTO } from '../../../dtos/order.dto';

@Component({
  selector: 'app-order-detail-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './order-detail-admin.component.html',
  styleUrl: './order-detail-admin.component.scss',
})
export class OrderDetailAdminComponent implements OnInit {
  orderId?: number;
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
  constructor(
    private orderService: OrderService,
    private router: ActivatedRoute,
    private route: Router
  ) {}
  ngOnInit(): void {
    this.getOrderDetails();
  }
  getOrderDetails(): void {
    this.orderId = Number(this.router.snapshot.paramMap.get('id'));
    this.orderService.getOrderById(this.orderId).subscribe({
      next: (response: OrderResponse) => {
        this.orderResponse.id = response.id;
        this.orderResponse.user_id = response.user_id;
        this.orderResponse.fullname = response.fullname;
        this.orderResponse.phone_number = response.phone_number;
        this.orderResponse.email = response.email;
        this.orderResponse.address = response.address;
        this.orderResponse.note = response.note;
        if (response.order_date) {
          this.orderResponse.order_date = new Date(
            response.order_date[0],
            response.order_date[1] - 1,
            response.order_date[2]
          );
        }
        this.orderResponse.status = response.status;
        this.orderResponse.total_money = response.total_money;
        this.orderResponse.shipping_method = response.shipping_method;
        if (response.shipping_date) {
          this.orderResponse.shipping_date = new Date(
            response.shipping_date[0],
            response.shipping_date[1] - 1,
            response.shipping_date[2]
          );
        }
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
      complete: () => {},
      error: (err) => {
        console.log(err);
      },
    });
  }
  saveOrder() {
    this.orderService
      .updateOrder(this.orderId, new OrderDTO(this.orderResponse))
      .subscribe({
        next: (response) => {
          this.route.navigate(['../'], { relativeTo: this.router });
        },
        error: (err) => {
          console.log(err);
        },
      });
  }
}
