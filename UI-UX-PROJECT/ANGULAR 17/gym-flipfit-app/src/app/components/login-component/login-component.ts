
// src/app/login-component/login.ts
import { Component } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login-component',
  // non-standalone component: do NOT put `imports` here
  standalone: false,
  templateUrl: './login-component.html',
  styleUrls: ['./login-component.css'], // <-- fixed: styleUrls (plural)
})
export class LoginComponent {
  loading = false;
  error = '';
  success = '';
  submitted = false;
  form!: FormGroup;

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(2)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
    console.log('[LoginComponent] constructor initialized');
  }

  get usernameError(): string {
    const c = this.form.controls['username'];
    if (c.hasError('required')) return 'Username is required';
    if (c.hasError('minlength')) return 'Username must be at least 2 characters';
    return '';
  }

  get passwordError(): string {
    const c = this.form.controls['password'];
    if (c.hasError('required')) return 'Password is required';
    if (c.hasError('minlength')) return 'Password must be at least 6 characters';
    return '';
  }

  
  onSubmit(): void {
    console.log('[LoginComponent] onSubmit fired');
    this.submitted = true;
    this.error = '';
    this.success = '';

    this.form.markAllAsTouched();
    if (this.form.invalid) {
      console.log('[LoginComponent] form invalid', this.form.value);
      return;
    }

    this.loading = true;
    const { username, password } = this.form.value;

    this.auth.login({ username: username!, password: password! }).subscribe({
      next: result => {
        this.loading = false;
        if (result.ok) {
          this.success = result.message;
          console.log('[LoginComponent] login success', result);
          // Redirect to a protected route
           this.router.navigateByUrl('/dashboard');
        } else {
          this.error = result.message;
          console.warn('[LoginComponent] login failed (API ERROR)', result);
        }
      },
      error: err => {
        this.loading = false;
        this.error = err.message ?? 'Login failed';
        console.error('[LoginComponent] login error (HTTP/Network)', err);
      }
    });
  }


}
