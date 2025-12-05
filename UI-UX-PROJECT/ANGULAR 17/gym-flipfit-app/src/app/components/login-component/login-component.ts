import { Component } from '@angular/core';

import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import {AuthService} from '../../services/auth.service'

@Component({
  selector: 'app-login-component',
  standalone: false,
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {

 
 constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }


  loading = false;
  error = '';
  success = '';
  submitted = false;
  form!: FormGroup;

  

  get emailError(): string {
    const c = this.form.controls['email'];
    if (c.hasError('required')) return 'Email is required';
    if (c.hasError('email')) return 'Enter a valid email';
    return '';
  }

  get passwordError(): string {
    const c = this.form.controls['password'];
    if (c.hasError('required')) return 'Password is required';
    if (c.hasError('minlength')) return 'Password must be at least 6 characters';
    return '';
  }

  onSubmit(): void {
    this.submitted = true;
    this.error = '';
    this.success = '';

    if (this.form.invalid) return;

    this.loading = true;
    const { email, password } = this.form.value;

    this.auth.login({ email: email!, password: password! }).subscribe({
      next: res => {
        this.loading = false;
        this.success = res.message;
        // Navigate to a protected page (add route later)
        // this.router.navigateByUrl('/dashboard');
      },
      error: err => {
        this.loading = false;
        this.error = err.message ?? 'Login failed';
      }
    });
  }
}
