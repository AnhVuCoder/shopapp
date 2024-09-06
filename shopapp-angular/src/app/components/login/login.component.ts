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
import { UserResponse } from '../../responses/user/UserResponse';
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
  rememberMe: boolean;
  userResponse?: UserResponse;
  constructor(
    private userService: UserService,
    private tokenService: TokenService,
    private router: Router,
    private roleService: RoleService
  ) {
    this.phoneNumber = '';
    this.password = '';
    this.rememberMe = false;
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
      next: (response: LoginResponse) => {
        const { token } = response;
        if (this.rememberMe) {
          this.tokenService.setToken(token);
          this.userService.getUserDetails(token).subscribe({
            next: (userResponse: any) => {
              debugger;
              this.userResponse = {
                ...userResponse,
              };
              this.userService.saveUserDetailsToLocalStorage(userResponse);
              if (this.userResponse?.role.id === 1)
                this.router.navigate(['/admin']);
              else if (this.userResponse?.role.id === 2)
                this.router.navigate(['/']);
            },
            error: (err) => {
              console.log(err);
            },
          });
        }
      },
      complete: () => {},
      error: (err) => {
        alert(err?.error?.message);
      },
    });
  }
}
