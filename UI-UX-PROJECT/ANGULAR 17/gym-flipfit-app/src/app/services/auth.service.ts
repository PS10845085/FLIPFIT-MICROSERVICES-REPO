
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
  };
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
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly SESSION_KEY = 'demo_session';
  private readonly baseUrl = 'http://localhost:8080/api'; // adjust as needed

  private readonly platformId = inject(PLATFORM_ID);
  private readonly isBrowser = isPlatformBrowser(this.platformId);

  constructor(private http: HttpClient) {}

  login(payload: LoginPayload): Observable<LoginResult> {
    const url = `${this.baseUrl}/auth/login`;
    return this.http.post<ApiLoginResponse>(url, payload).pipe(
      map((res): LoginResult => {
        if (res.status === 'SUCCESS' && res.data) {
          const { userName, userId, token } = res.data;
          return {
            ok: true,
            message: res.message ?? 'Login successful',
            username: userName,
            userId,
            token
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
              userId: result.userId!
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

  logout(): void {
    if (this.isBrowser) {
      try {
        localStorage.removeItem(this.SESSION_KEY);
      } catch {
        // ignore
      }
    }
  }

  currentUser(): { token?: string; username?: string; userId?: number } | null {
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
