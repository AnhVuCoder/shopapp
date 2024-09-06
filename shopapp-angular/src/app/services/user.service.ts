import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/user/register.dto';
import { LoginDTO } from '../dtos/user/login.dto';
import { environment } from '../environments/environment';
import { UserResponse } from '../responses/user/UserResponse';
import { UpdatedUserDTO } from '../dtos/user/update.user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiRegisterUrl = `${environment.apiBaseUrl}/users/register`;
  private apiLoginUrl = `${environment.apiBaseUrl}/users/login`;
  private apiUserDetailsUrl = `${environment.apiBaseUrl}/users/details`;
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
  getUserDetails(token: string): Observable<any> {
    return this.http.post(this.apiUserDetailsUrl, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    });
  }
  private createHeaders() {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi',
    });
  }
  saveUserDetailsToLocalStorage(userResponse: UserResponse) {
    try {
      const userResponseJSON = JSON.stringify(userResponse);
      localStorage.setItem('userResponse', userResponseJSON);
      console.log('Success save user to local storage');
    } catch (error) {
      console.log('Error save user detail to local storage');
    }
  }
  getUserDetailsFromLocalStorage(): any {
    try {
      const userDetailJSON = localStorage.getItem('userResponse');
      if (userDetailJSON == null || userDetailJSON == undefined) return null;
      const userDetail = JSON.parse(userDetailJSON);
      return userDetail;
    } catch (error) {
      console.log('Error get user detail from local storage');
      return null;
    }
  }
  removeUserDetailsFromLocalStorage() {
    try {
      localStorage.removeItem('userResponse');
      console.log('Success remove user detail from local storage');
    } catch (error) {
      console.log('Error remove user detail from local storage');
    }
  }
  updateUser(token: string, updatedUserDTO: UpdatedUserDTO): Observable<any> {
    let userResponse = this.getUserDetailsFromLocalStorage();
    return this.http.put(
      `${this.apiUserDetailsUrl}/${userResponse?.id}`,
      updatedUserDTO,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        }),
      }
    );
  }
}
