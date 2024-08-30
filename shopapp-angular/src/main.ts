import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { RegisterComponent } from './app/components/register/register.component';
import { LoginComponent } from './app/components/login/login.component';
import { HomeComponent } from './app/components/home/home.component';
import { DetailProductComponent } from './app/components/detail-product/detail-product.component';
import { OrderDetailComponent } from './app/components/order-confirm/order.detail.component';
import { OrderComponent } from './app/components/order/order.component';

bootstrapApplication(OrderDetailComponent, appConfig).catch((err) =>
  console.error(err)
);
