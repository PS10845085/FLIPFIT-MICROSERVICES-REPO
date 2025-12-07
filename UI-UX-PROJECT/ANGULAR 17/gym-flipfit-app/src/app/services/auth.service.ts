
// src/app/services/auth.service.ts
import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, tap, catchError } from 'rxjs/operators';

export interface LoginPayload {
  username: string;
  password: string;
}

export interface ApiLoginSuccess {
  status: 'SUCCESS';
  message: string;
  data: {
    userName: string;
    userId: number;
    token: string;
    userRoleId: 1 | 2 | 3;

  };
}

export interface SessionData {
  token: string;
  username: string;
  userId: number;
  userRoleId?: 1 | 2 | 3; // <- add (optional)
}

export interface ApiLoginError {
  status: 'ERROR';
  message: string;
  data: null;
}

type ApiLoginResponse = ApiLoginSuccess | ApiLoginError;

export interface LoginResult {
  ok: boolean;
  message: string;
  username?: string;
  userId?: number;
  token?: string;
  userRoleId?: 1 | 2 | 3;
}


// --- Add these interfaces at top (near LoginPayload) ---

export interface RegisterPayload {
  username: string;
  password: string;
  roleid: number;
  centerid: number;
  firstname: string;
  lastname: string;
  email: string;
  mobile: number;
  status: 'PENDING' | 'ACTIVE' | 'INACTIVE'; // Adjust as per your backend enum
  line1: string;
  line2?: string;
  city: string;
  state: string;
  postalcode: string;
  country: string;
}

export interface ApiRegisterResponse {
  status: 'SUCCESS' | 'ERROR';
  message: string;
  data: any; // Could be created user object; refine as needed
}


@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly SESSION_KEY = 'demo_session';
  private readonly USER_SERVICE_BASE_URL = 'http://localhost:8080/api'; // adjust as needed

  private readonly platformId = inject(PLATFORM_ID);
  private readonly isBrowser = isPlatformBrowser(this.platformId);

  constructor(private http: HttpClient) {}

  login(payload: LoginPayload): Observable<LoginResult> {
    const url = `${this.USER_SERVICE_BASE_URL}/auth/login`;
    return this.http.post<ApiLoginResponse>(url, payload).pipe(
      map((res): LoginResult => {
        if (res.status === 'SUCCESS' && res.data) {
          const { userName, userId, token, userRoleId } = res.data;
          return {
            ok: true,
            message: res.message ?? 'Login successful',
            username: userName,
            userId,
            token,
            userRoleId
          };
        }
        return {
          ok: false,
          message: res.message ?? 'Login failed'
        };
      }),
      tap((result) => {
        // Only persist on the browser
        if (result.ok && this.isBrowser) {
          try {
            const session = {
              token: result.token!,
              username: result.username!,
              userId: result.userId!,
              userRoleId: result.userRoleId,

            };
            localStorage.setItem(this.SESSION_KEY, JSON.stringify(session));
            console.log('[AuthService] Session saved:', session);
          } catch (e) {
            console.error('[AuthService] Failed to save session:', e);
          }
        }
      }),
      catchError((err: HttpErrorResponse) => {
        const backendMsg =
          (err.error && typeof err.error === 'object' && 'message' in err.error)
            ? (err.error as any).message
            : undefined;
        const message = backendMsg ?? err.message ?? 'Login failed';
        return throwError(() => new Error(message));
      })
    );
  }

// --- Add this method inside AuthService class ---
register(payload: RegisterPayload): Observable<ApiRegisterResponse> {
  const url = `${this.USER_SERVICE_BASE_URL}/auth/register`;
  return this.http.post<ApiRegisterResponse>(url, payload).pipe(
    catchError((err: HttpErrorResponse) => {
      const backendMsg =
        (err.error && typeof err.error === 'object' && 'message' in err.error)
          ? (err.error as any).message
          : undefined;
      const message = backendMsg ?? err.message ?? 'Registration failed';
      return throwError(() => new Error(message));
    })
  );
}

  logout(): void {
    if (this.isBrowser) {
      try {
        localStorage.removeItem(this.SESSION_KEY);
      } catch {
        // ignore
      }
    }
  }

// Optional convenience:

getCurrentRoleId(): 1 | 2 | 3 | undefined {
  return this.currentUser()?.userRoleId as 1 | 2 | 3 | undefined;
}


// optional convenience
getDashboardPathForRole(roleId?: 1 | 2 | 3): string {
  switch (roleId) {
    case 1: return '/dashboard-owner';
    case 2: return '/dashboard-admin';
    case 3: return '/dashboard-customer';
    default: return '/login';
  }
}

isLoggedIn(): boolean {
  const session = this.currentUser();
  return !!(session && session.token);
}

  currentUser(): { token?: string; username?: string; userId?: number, userRoleId?: number } | null {
    if (!this.isBrowser) {
      return null;
    }
    try {
      const raw = localStorage.getItem(this.SESSION_KEY);
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  }
}
