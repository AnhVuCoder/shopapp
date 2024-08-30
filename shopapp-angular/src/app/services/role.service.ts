import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class RoleService {
  private apiRoleUrl = `${environment.apiBaseUrl}/roles`;
  constructor(private http: HttpClient) {}
  getRoles(): Observable<any> {
    return this.http.get(this.apiRoleUrl);
  }
}
