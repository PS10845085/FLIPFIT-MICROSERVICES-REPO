
// src/app/services/customer.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

export interface CustomerAddress {
  id: number;
  line1: string;
  line2?: string;
  city: string;
  state: string;
  country: string;
  postalcode: string;
}

export interface Center{
    id: number,
    email_id: string,
    name: string,
    owner_id:number,
    phone_no : number,
    status: string,
    address_id:number
}

export interface CustomerUser {
  id: number;
  username: string;
  password?: string;
  createdAt: number[];
  roleid: 1 | 2 | 3;
  status: 'ACTIVE' | 'INACTIVE' | 'PENDING';
}

export interface Customer {
  id: number;
  firstname: string;
  lastname: string;
  email: string;
  mobile: number;
  center: Center;
  address: CustomerAddress;
  createdAt: number[];
  updatedAt: number[];
  user: CustomerUser;
}

export interface ApiCustomerListResponse {
  status: 'SUCCESS' | 'ERROR';
  message: string;
  data: Customer[] | null;
}

@Injectable({ providedIn: 'root' })
export class CustomerService {
  private readonly ADMIN_SERVICE_BASE_URL = 'http://localhost:8082/api';

  constructor(private http: HttpClient) {}

  list(): Observable<Customer[]> {
    const url = `${this.ADMIN_SERVICE_BASE_URL}/admin/customer-list`;
    // If this endpoint is public, we can skip auth header (matches your other services)
    return this.http.get<ApiCustomerListResponse>(url, { headers: { skipAuth: 'true' } }).pipe(
      map(res => (res.status === 'SUCCESS' && Array.isArray(res.data)) ? res.data : []),
      catchError((err: HttpErrorResponse) => {
        const message =
          (err.error && typeof err.error === 'object' && 'message' in err.error)
            ? (err.error as any).message
            : err.message ?? 'Failed to fetch customers';
        return throwError(() => new Error(message));
      })
    );
  }
}
