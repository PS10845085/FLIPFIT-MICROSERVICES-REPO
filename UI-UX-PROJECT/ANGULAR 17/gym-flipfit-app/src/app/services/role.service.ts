
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

export interface Role {
  roleId: number;
  roleName: string;
  description?: string;
}

export interface ApiRolesResponse {
  status: 'SUCCESS' | 'ERROR';
  message: string;
  data: Role[] | null;
}

@Injectable({ providedIn: 'root' })
export class RoleService {
  // If you have environments, prefer environment.apiBaseUrl5104
  private readonly ROLE_SERVICE_BASE_URL = 'http://localhost:5104/api';

  constructor(private http: HttpClient) {}

  getRoles(): Observable<Role[]> {
    const url = `${this.ROLE_SERVICE_BASE_URL}/roles`;
    return this.http.get<ApiRolesResponse>(url, { headers: { skipAuth: 'true' } }).pipe(
      map((res) => {
        if (res.status === 'SUCCESS' && Array.isArray(res.data)) {
          return res.data;
        }
        return [];
      }),
      catchError((err: HttpErrorResponse) => {
        const message =
          (err.error && typeof err.error === 'object' && 'message' in err.error)
            ? (err.error as any).message
            : err.message ?? 'Failed to fetch roles';
        return throwError(() => new Error(message));
      })
    );
  }
}
