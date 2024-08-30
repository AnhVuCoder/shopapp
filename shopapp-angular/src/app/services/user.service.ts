import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/user/register.dto';
import { LoginDTO } from '../dtos/user/login.dto';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiRegisterUrl = `${environment.apiBaseUrl}/users/register`;
  private apiLoginUrl = `${environment.apiBaseUrl}/users/login`;
  private apiConfig = {
    headers: this.createHeaders(),
  };
  constructor(private http: HttpClient) {}
  register(registerDTO: RegisterDTO): Observable<any> {
    return this.http.post(this.apiRegisterUrl, registerDTO, this.apiConfig);
  }
  login(loginDTO: LoginDTO): Observable<any> {
    return this.http.post(this.apiLoginUrl, loginDTO, this.apiConfig);
  }
  private createHeaders() {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi',
    });
  }
}
