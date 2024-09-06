import { Component, OnInit } from '@angular/core';
import { OrderResponse } from '../../../responses/order/OrderResponse';
import { OrderService } from '../../../services/order.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-admin.component.html',
  styleUrl: './order-admin.component.scss',
})
export class OrderAdminComponent implements OnInit {
  orders?: OrderResponse[];
  currentPage: number = 1;
  itemsPerPage: number = 10;
  pages: number[] = [];
  totalPages: number = 0;
  keyword: string = '';
  visiblePages: number[] = [];
  constructor(private orderService: OrderService, private router: Router) {}
  ngOnInit(): void {
    this.getAllOrders(this.keyword, this.currentPage, this.itemsPerPage);
  }
  getAllOrders(keyword: string, page: number, limit: number) {
    this.orderService.getAllOrder(keyword, page, limit).subscribe({
      next: (response: any) => {
        console.log(response);
        this.orders = response.order_response_list;
        this.orders?.forEach((item) => {
          if (item.order_date) {
            item.order_date = new Date(
              item.order_date[0],
              item.order_date[1] - 1,
              item.order_date[2]
            )
              .toISOString()
              .substring(0, 10);
          }
          if (item.shipping_date) {
            item.shipping_date = new Date(
              item.shipping_date[0],
              item.shipping_date[1] - 1,
              item.shipping_date[2]
            )
              .toISOString()
              .substring(0, 10);
          }
        });
        this.totalPages = response.total_pages;
        this.visiblePages = this.generateVisiblePages(
          this.currentPage,
          this.totalPages
        );
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
  generateVisiblePages(currentPage: number, totalPages: number) {
    let maxVisiblePage = 5;
    let halfVisblePage = Math.floor(maxVisiblePage / 2);
    let startPage = Math.max(currentPage - halfVisblePage, 1);
    let endPage = Math.min(startPage + maxVisiblePage - 1, totalPages);
    if (endPage - startPage + 1 < maxVisiblePage) {
      startPage = Math.max(endPage - startPage + 1, 1);
    }
    return new Array(endPage - startPage + 1)
      .fill(0)
      .map((_, index) => startPage + index);
  }
  onPageChange(page: number) {
    this.currentPage = page;
    this.getAllOrders(this.keyword, this.currentPage, this.itemsPerPage);
  }
  viewDetails(order: OrderResponse) {
    this.router.navigate(['/admin/orders', order.id]);
  }
  deleteOrder(id: number) {
    const confirmation = window.confirm('Are you want to delete this order ?');
    if (confirmation) {
      this.orderService.deleteOrder(id).subscribe({
        next: () => {
          location.reload();
        },
        error: (err) => {
          console.log(err);
        },
      });
    }
  }
}
