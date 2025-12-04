
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

interface LoginResponse {
  status: string;
  message: string;
  data: {
    userName: string;
    token: string;
  };
}

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class LoginComponent {
  username = '';
  password = '';
  loading = false;
  errorMessage = '';

  private readonly LOGIN_URL = 'http://localhost:8080/api/auth/login';

  constructor(private http: HttpClient) {}

  onLogin(): void {
    this.errorMessage = '';
    this.loading = true;

    const body = {
      username: this.username,
      password: this.password,
    };

    this.http.post<LoginResponse>(this.LOGIN_URL, body).subscribe({
      next: (response) => {
        this.loading = false;

        // Expecting { status, message, data: { userName, token } }
        if (response?.data?.token) {
          localStorage.setItem('token', response.data.token);
          // Optional: navigate or show success
          alert(`Welcome ${response.data.userName}!`);
        } else {
          this.errorMessage = 'Token not found in response.';
        }
      },
      error: (err: HttpErrorResponse) => {
        this.loading = false;
        this.errorMessage =
          err.error?.message ??
          err.statusText ??
          'Login failed. Please check your credentials.';
      },
    });
  }
}

