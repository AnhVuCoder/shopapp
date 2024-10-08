import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DetailProductComponent } from './components/detail-product/detail-product.component';
import { OrderComponent } from './components/order/order.component';
import { OrderDetailComponent } from './components/order-confirm/order.detail.component';
import { authGuard } from './auth/auth.guard';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { AdminComponent } from './components/admin/admin.component';
import { adminGuard } from './auth/admin.guard';
import { OrderAdminComponent } from './components/admin/order-admin/order-admin.component';
import { ProductAdminComponent } from './components/admin/product-admin/product-admin.component';
import { CategoryAdminComponent } from './components/admin/category-admin/category-admin.component';
import { OrderDetailAdminComponent } from './components/admin/order-detail-admin/order-detail-admin.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'products/:id', component: DetailProductComponent },
  { path: 'orders', component: OrderComponent, canActivate: [authGuard] },
  { path: 'orders/:id', component: OrderDetailComponent },
  {
    path: 'profile',
    component: UserProfileComponent,
    canActivate: [authGuard],
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [adminGuard],
    children: [
      {
        path: 'orders',
        component: OrderAdminComponent,
      },
      {
        path: 'orders/:id',
        component: OrderDetailAdminComponent,
      },
      {
        path: 'products',
        component: ProductAdminComponent,
      },
      {
        path: 'categories',
        component: CategoryAdminComponent,
      },
    ],
  },
];
