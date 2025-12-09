
// src/app/services/owner.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

export interface OwnerAddress {
  id: number;
  line1: string;
  line2?: string;
  city: string;
  state: string;
  country: string;
  postalcode: string;
}

// NOTE: In your API sample, `center` on owner is an address-like object.
// If in your backend a Center has a `name` elsewhere, adjust this type accordingly.
export interface OwnerCenterAddress {
  id: number;
  line1: string;
  line2?: string;
  city: string;
  state: string;
  country: string;
  postalcode: string;
}

export interface OwnerUser {
  id: number;
  username: string;
  password?: string;
  createdAt: number[];
  roleid: 1 | 2 | 3; // 1: OWNER, 2: ADMIN, 3: CUSTOMER
  status: 'ACTIVE' | 'INACTIVE' | 'PENDING';
}

export interface Owner {
  id: number;
  firstname: string;
  lastname: string;
  email: string;
  mobile: number;
  center: OwnerCenterAddress | null; // as per sample payload
  address: OwnerAddress | null;
  createdAt: number[];
  updatedAt: number[];
  user: OwnerUser | null;
}

export interface ApiOwnerListResponse {
  status: 'SUCCESS' | 'ERROR';
  message: string;
  data: Owner[] | null;
}

@Injectable({ providedIn: 'root' })
export class OwnerService {
  private readonly ADMIN_SERVICE_BASE_URL = 'http://localhost:8082/api';

  constructor(private http: HttpClient) {}

  list(): Observable<Owner[]> {
    const url = `${this.ADMIN_SERVICE_BASE_URL}/admin/owner-list`;
    return this.http.get<ApiOwnerListResponse>(url, { headers: { skipAuth: 'true' } }).pipe(
      map(res => (res.status === 'SUCCESS' && Array.isArray(res.data)) ? res.data : []),
      catchError((err: HttpErrorResponse) => {
        const message =
          (err.error && typeof err.error === 'object' && 'message' in err.error)
            ? (err.error as any).message
            : err.message ?? 'Failed to fetch owners';
        return throwError(() => new Error(message));
      })
    );
  }
}
