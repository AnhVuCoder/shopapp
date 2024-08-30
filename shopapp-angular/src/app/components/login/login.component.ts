import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule, NgForm } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { LoginResponse } from '../../responses/user/LoginResponse';
import { TokenService } from '../../services/token.service';
import { Router } from '@angular/router';
import { RoleService } from '../../services/role.service';
import { Role } from '../../models/role';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string;
  password: string;
  roles!: Role[];
  selectedRole!: Role;
  rememberMe!: boolean;
  constructor(
    private userService: UserService,
    private tokenService: TokenService,
    private router: Router,
    private roleService: RoleService
  ) {
    this.phoneNumber = '';
    this.password = '';
  }
  ngOnInit(): void {
    debugger;
    this.roleService.getRoles().subscribe({
      next: (roles: any) => {
        this.roles = roles;
        if (this.roles.length > 0) {
          this.selectedRole = this.roles[0];
        }
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
  onPhoneNumberChange() {}
  onLogin() {
    const loginDTO = {
      phone_number: this.phoneNumber,
      password: this.password,
      role_id: this.selectedRole?.id ?? 1,
    };
    this.userService.login(loginDTO).subscribe({
      next: (responese: LoginResponse) => {
        debugger;
        const { token } = responese;
        if (this.rememberMe) {
          this.tokenService.setToken(token);
        }
      },
      complete: () => {},
      error: (err) => {
        alert(err?.error?.message);
      },
    });
  }
}
