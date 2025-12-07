
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

export interface CenterAddress {
  id: number;
  line1: string;
  line2?: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
}

export interface Center {
  id: number;
  ownerId: number;
  name: string;
  emailId?: string;
  phoneNo?: string;
  address: CenterAddress;
  slots: any[];
  status: 'ACTIVE' | 'INACTIVE' | 'PENDING';
}

export interface ApiCentersResponse {
  status: 'SUCCESS' | 'ERROR';
  message: string;
  data: Center[] | null;
}

@Injectable({ providedIn: 'root' })
export class CenterService {
  private readonly CENTER_SERVICE_BASE_URL = 'http://localhost:8081/api';

  constructor(private http: HttpClient) {}

  getCenters(): Observable<Center[]> {
    const url = `${this.CENTER_SERVICE_BASE_URL}/centers`;
    return this.http.get<ApiCentersResponse>(url , { headers: { skipAuth: 'true' } } ).pipe(
      map((res) => (res.status === 'SUCCESS' && Array.isArray(res.data)) ? res.data : []),
      catchError((err: HttpErrorResponse) => {
        const message =
          (err.error && typeof err.error === 'object' && 'message' in err.error)
            ? (err.error as any).message
            : err.message ?? 'Failed to fetch centers';
        return throwError(() => new Error(message));
      })
    );
  }

  getActiveCenters(): Observable<Center[]> {
    const url = `${this.CENTER_SERVICE_BASE_URL}/active-centers`;
    return this.http.get<ApiCentersResponse>(url , { headers: { skipAuth: 'true' } } ).pipe(
      map((res) => (res.status === 'SUCCESS' && Array.isArray(res.data)) ? res.data : []),
      catchError((err: HttpErrorResponse) => {
        const message =
          (err.error && typeof err.error === 'object' && 'message' in err.error)
            ? (err.error as any).message
            : err.message ?? 'Failed to fetch centers';
        return throwError(() => new Error(message));
      })
    );
  }
}
