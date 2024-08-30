import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { Product } from '../../models/product';
import { OrderDTO } from '../../dtos/order.dto';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CartService } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';
import { OrderService } from '../../services/order.service';
import { environment } from '../../environments/environment';
import { Validator } from 'class-validator';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss',
})
export class OrderComponent implements OnInit {
  public cartItems: { product: Product; quantity: number }[] = [];
  public couponCode!: string;
  public orderForm: FormGroup;
  public orderData: OrderDTO = {
    user_id: 1,
    phone_number: '',
    address: '',
    note: '',
    fullname: '',
    email: '',
    payment_method: '',
    shipping_method: '',
    total_money: 0,
    coupon_code: '',
    cart_items: [],
  };
  public totalAmount = 0;
  ngOnInit(): void {
    const cart = this.cartService.getCart();
    const productIds = Array.from(cart.keys());
    this.productService.getProductByIds(productIds).subscribe({
      next: (response) => {
        this.cartItems = productIds.map((productId) => {
          const product = response.find((product) => product.id === productId);
          if (product) {
            product.url = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
          }
          return {
            product: product!,
            quantity: cart.get(productId)!,
          };
        });
      },
      complete: () => {
        this.calculateTotal();
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private fb: FormBuilder,
    private productService: ProductService
  ) {
    this.orderForm = this.fb.group({
      fullname: ['', [Validators.required]],
      email: ['', [Validators.email, Validators.required]],
      phone_number: ['', [Validators.required, Validators.minLength(6)]],
      address: ['', [Validators.required, Validators.minLength(5)]],
      note: [''],
      shipping_method: ['express'],
      payment_method: ['cod'],
    });
  }
  onPlaceOrder() {
    if (this.orderForm.valid) {
      this.orderData = { ...this.orderData, ...this.orderForm.value };
      this.calculateTotal();
      this.orderData.total_money = this.totalAmount;
      this.orderData.coupon_code = this.couponCode;
      this.orderData.cart_items = this.cartItems.map((p) => {
        return {
          product_id: p.product.id,
          quantity: p.quantity,
        };
      });
      this.orderService.createOrder(this.orderData).subscribe({
        next: (response) => {
          console.log(response);
        },
        complete: () => {
          this.calculateTotal();
        },
        error: (err) => console.log(err),
      });
    }
  }
  calculateTotal(): void {
    this.totalAmount = this.cartItems.reduce(
      (total, item) => total + item.product.price * item.quantity,
      0
    );
  }
}
