import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { NgForm } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { UserService } from '../../services/user.service';
import { RegisterDTO } from '../../dtos/user/register.dto';
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, FormsModule, NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent implements OnInit {
  @ViewChild('registerForm') registerForm!: NgForm;
  // Khai báo các trường dữ liệu có trong form
  phoneNumber: string;
  password: string;
  retypePassword: string;
  fullName: string;
  address: string;
  isAccepted: boolean;
  dateOfBirth: Date;
  constructor(private router: Router, private userService: UserService) {
    this.phoneNumber = '';
    this.retypePassword = '';
    this.password = '';
    this.fullName = '';
    this.address = '';
    this.isAccepted = true;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
  }
  ngOnInit(): void {}
  onPhoneChange() {}
  // Register
  onRegister() {
    const registerDTO: RegisterDTO = {
      fullname: this.fullName,
      phone_number: this.phoneNumber,
      password: this.password,
      address: this.address,
      retype_password: this.retypePassword,
      date_of_birth: this.dateOfBirth,
      facebook_account_id: 0,
      google_account_id: 0,
      role_id: 1,
    };
    this.userService.register(registerDTO).subscribe({
      next: (response) => {
        console.log(response);
        this.router.navigate(['/login']);
      },
      complete: () => {},
      error: (err) => {
        console.log(err);
      },
    });
  }
  // Check password match
  checkPasswordMatch() {
    if (this.password !== this.retypePassword) {
      this.registerForm.controls['retypePassword']?.setErrors({
        passwordMismatch: true,
      });
    } else {
      this.registerForm.controls['retypePassword']?.setErrors(null);
    }
  }
  // Check age valid
  checkAge() {
    if (this.dateOfBirth) {
      let today = new Date();
      let birthday = new Date(this.dateOfBirth);
      let age = today.getFullYear() - birthday.getFullYear();
      let monthSpace = today.getMonth() - birthday.getMonth();
      if (
        monthSpace < 0 ||
        (monthSpace === 0 && today.getDate() < birthday.getDate())
      ) {
        --age;
      }
      if (age < 18) {
        this.registerForm.controls['dateOfBirth'].setErrors({
          invalidAge: true,
        });
      } else {
        this.registerForm.controls['dateOfBirth'].setErrors(null);
      }
    }
  }
}
