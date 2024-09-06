import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { UserService } from '../../services/user.service';
import { TokenService } from '../../services/token.service';
import {
  Router,
  RouterLink,
  RouterLinkActive,
  RouterOutlet,
} from '@angular/router';
import { UserResponse } from '../../responses/user/UserResponse';
import { OrderAdminComponent } from './order-admin/order-admin.component';
import { ProductAdminComponent } from './product-admin/product-admin.component';
import { CategoryAdminComponent } from './category-admin/category-admin.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    CommonModule,
    FooterComponent,
    HeaderComponent,
    RouterLink,
    RouterLinkActive,
    RouterOutlet,
    OrderAdminComponent,
    ProductAdminComponent,
    CategoryAdminComponent,
  ],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss',
})
export class AdminComponent implements OnInit {
  userResponse?: UserResponse;
  adminPage: string = 'orders';
  constructor(
    private userService: UserService,
    private tokenService: TokenService,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.userResponse = this.userService.getUserDetailsFromLocalStorage();
    this.router.navigate(['/admin/orders']);
  }
  logout() {
    this.tokenService.removeToken();
    this.userService.removeUserDetailsFromLocalStorage();
    this.userResponse = this.userService.getUserDetailsFromLocalStorage();
    this.router.navigate(['/login']);
  }
  showAdminPage(componentName: string): void {
    if (componentName == 'orders') {
      this.router.navigate(['/admin/orders']);
    } else if (componentName == 'categories') {
      this.router.navigate(['/admin/categories']);
    } else if (componentName == 'products') {
      this.router.navigate(['/admin/products']);
    }
  }
}
