import { CartItemDTO } from './cart.item.dto';

export class OrderDTO {
  user_id: number;
  phone_number: string;
  address: string;
  note: string;
  fullname: string;
  email: string;
  payment_method: string;
  shipping_method: string;
  total_money: number;
  coupon_code: string;
  cart_items: CartItemDTO[];
  constructor(data: any) {
    this.user_id = data.user_id;
    this.phone_number = data.phone_number;
    this.address = data.address;
    this.note = data.note;
    this.fullname = data.fullname;
    this.email = data.email;
    this.payment_method = data.payment_method;
    this.shipping_method = data.shipping_method;
    this.coupon_code = data.coupon_code;
    this.total_money = data.total_money;
    this.cart_items = data.cart_items;
  }
}
