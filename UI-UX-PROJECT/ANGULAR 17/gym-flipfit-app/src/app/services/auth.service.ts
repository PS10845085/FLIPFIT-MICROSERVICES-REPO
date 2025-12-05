
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';

export interface LoginPayload { email: string; password: string; }
export interface RegisterPayload { name: string; email: string; password: string; }

@Injectable({ providedIn: 'root' })
export class AuthService {
  private STORAGE_KEY = 'demo_users';
  private SESSION_KEY = 'demo_session';

  private getUsers(): Record<string, { name: string; password: string }> {
    const raw = localStorage.getItem(this.STORAGE_KEY);
    return raw ? JSON.parse(raw) : {};
  }

  private setUsers(users: Record<string, { name: string; password: string }>): void {
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(users));
  }

  register(payload: RegisterPayload): Observable<{ message: string }> {
    const users = this.getUsers();
    const email = payload.email.toLowerCase();

    if (users[email]) {
      return throwError(() => new Error('Email already registered.'));
    }
    users[email] = { name: payload.name, password: payload.password };
    this.setUsers(users);
    return of({ message: 'Registration successful.' });
  }

  login(payload: LoginPayload): Observable<{ message: string; user: { name: string; email: string } }> {
    const users = this.getUsers();
    const email = payload.email.toLowerCase();
    const user = users[email];

    if (!user || user.password !== payload.password) {
      return throwError(() => new Error('Invalid email or password.'));
    }
    localStorage.setItem(this.SESSION_KEY, JSON.stringify({ email, name: user.name }));
    return of({ message: 'Login successful.', user: { email, name: user.name } });
  }

  logout(): void {
    localStorage.removeItem(this.SESSION_KEY);
  }

  currentUser(): { email: string; name: string } | null {
    const raw = localStorage.getItem(this.SESSION_KEY);
    return raw ? JSON.parse(raw) : null;
  }
}
