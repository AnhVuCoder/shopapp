import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { TokenService } from '../../services/token.service';
import { UserResponse } from '../../responses/user/UserResponse';
import { ValidationError } from 'class-validator';
import { UpdatedUserDTO } from '../../dtos/user/update.user';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss',
})
export class UserProfileComponent implements OnInit {
  public userForm!: FormGroup;
  token?: any;
  public userResponse: any;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private activatedRouter: ActivatedRoute,
    private userService: UserService,
    private tokenService: TokenService
  ) {
    this.userForm = this.fb.group(
      {
        fullname: [''],
        // Not recommended
        phone_number: [''],
        address: ['', [Validators.minLength(3)]],
        password: ['', [Validators.minLength(3)]],
        retype_password: ['', Validators.minLength(3)],
        date_of_birth: [Date.now()],
      },
      {
        validators: this.passwordMatchValidator(),
      }
    );
  }
  ngOnInit(): void {
    this.token = this.tokenService.getToken() ?? '';
    this.userService.getUserDetails(this.token).subscribe({
      next: (response) => {
        this.userResponse = {
          ...response,
          date_of_birth: new Date(
            response.date_of_birth[0],
            response.date_of_birth[1] - 1,
            response.date_of_birth[2] + 1
          ),
        };
        this.userForm.patchValue({
          fullname: this.userResponse.fullname ?? '',
          address: this.userResponse.address ?? '',
          date_of_birth: this.userResponse.date_of_birth
            .toISOString()
            .substring(0, 10),
        });
        this.userService.saveUserDetailsToLocalStorage(this.userResponse);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
  passwordMatchValidator(): ValidatorFn {
    debugger;
    return (formGroup: AbstractControl): ValidationErrors | null => {
      const password = formGroup.get('password')?.value;
      const retypePassword = formGroup.get('retype_password')?.value;
      if (password !== retypePassword) {
        return { passwordMismatch: true };
      }
      return null;
    };
  }
  save() {
    debugger;
    if (this.userForm.valid) {
      const updatedUser: UpdatedUserDTO = {
        fullname: this.userForm.get('fullname')?.value ?? '',
        address: this.userForm.get('address')?.value ?? '',
        password: this.userForm.get('password')?.value ?? '',
        retype_password: this.userForm.get('retype_password')?.value ?? '',
        date_of_birth: this.userForm.get('date_of_birth')?.value ?? Date.now(),
      };
      this.userService.updateUser(this.token, updatedUser).subscribe({
        next: (response) => {
          this.userService.removeUserDetailsFromLocalStorage();
          this.tokenService.removeToken();
          this.router.navigate(['/login']);
        },
      });
    } else {
      if (this.userForm.hasError('passwordMismatch')) {
        alert('Mật khẩu và xác nhận mật khẩu không trùng khớp');
      }
    }
  }
}
