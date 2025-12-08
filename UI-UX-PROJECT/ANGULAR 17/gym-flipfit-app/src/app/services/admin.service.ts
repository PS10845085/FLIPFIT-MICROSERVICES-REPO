
// src/app/services/gym-user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

export interface ApiResponse<T> {
  status: 'SUCCESS' | 'ERROR';
  message: string;
  data: T | null;
}

export interface GymUser {
  id: number;
  username: string;
  password?: string;
  createdAt: number[]; // [yyyy, mm, dd, hh, mm, ss, nanos]
  roleid: 1 | 2 | 3;
  status: 'ACTIVE' | 'INACTIVE' | 'PENDING';
}

export interface UpdateStatusPayload {
  id: number;
  status: 'ACTIVE' | 'INACTIVE' | 'PENDING';
}

@Injectable({ providedIn: 'root' })
export class GymAdminService {
  private readonly BASE_URL = 'http://localhost:8082/api';

  constructor(private http: HttpClient) {}

  updateStatus(payload: UpdateStatusPayload): Observable<GymUser> {
    const url = `${this.BASE_URL}/admin/update-user-status`;
    return this.http.put<ApiResponse<GymUser>>(url, payload).pipe(
      map(res => {
        if (res.status === 'SUCCESS' && res.data) return res.data;
        throw new Error(res.message || 'Failed to update status');
      }),
      catchError((err: HttpErrorResponse) => {
        const msg =
          (err.error && typeof err.error === 'object' && 'message' in err.error)
            ? (err.error as any).message
            : err.message ?? 'Failed to update status';
        return throwError(() => new Error(msg));
      })
    );
  }
}
