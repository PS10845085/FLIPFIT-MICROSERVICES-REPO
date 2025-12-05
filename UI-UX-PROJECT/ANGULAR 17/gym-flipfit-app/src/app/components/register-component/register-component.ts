
import { Component } from '@angular/core';
import { FormBuilder, Validators, FormControl, FormGroup, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router } from '@angular/router';
import {AuthService} from '../../services/auth.service'


type RegisterForm = FormGroup<{
  name: FormControl<string>;
  email: FormControl<string>;
  password: FormControl<string>;
  confirmPassword: FormControl<string>;
}>;

@Component({
  selector: 'app-register-component',
  standalone: false,
  templateUrl: './register-component.html',
  styleUrl: './register-component.css',
})
export class RegisterComponent {

  loading = false;
  error = '';
  success = '';
  submitted = false;

  form: RegisterForm;

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.group({
      name: this.fb.control('', { validators: [Validators.required, Validators.minLength(2)], nonNullable: true }),
      email: this.fb.control('', { validators: [Validators.required, Validators.email], nonNullable: true }),
      password: this.fb.control('', { validators: [Validators.required, Validators.minLength(6)], nonNullable: true }),
      confirmPassword: this.fb.control('', { validators: [Validators.required], nonNullable: true })
    }, { validators: [this.passwordsMatchValidator] });
  }

  passwordsMatchValidator(group: AbstractControl): ValidationErrors | null {
    const password = group.get('password')?.value as string;
    const confirm = group.get('confirmPassword')?.value as string;
    return password && confirm && password === confirm ? null : { passwordMismatch: true };
  }

  get nameError(): string {
    const c = this.form.controls.name;
    if (c.hasError('required')) return 'Name is required';
    if (c.hasError('minlength')) return 'Name must be at least 2 characters';
    return '';
  }
  get emailError(): string {
    const c = this.form.controls.email;
    if (c.hasError('required')) return 'Email is required';
    if (c.hasError('email')) return 'Enter a valid email';
    return '';
  }
  get passwordError(): string {
    const c = this.form.controls.password;
    if (c.hasError('required')) return 'Password is required';
    if (c.hasError('minlength')) return 'Password must be at least 6 characters';
    return '';
  }
  get confirmPasswordError(): string {
    const c = this.form.controls.confirmPassword;
    if (c.hasError('required')) return 'Confirm password is required';
    if (this.form.hasError('passwordMismatch')) return 'Passwords do not match';
    return '';
  }

  onSubmit(): void {
    this.submitted = true;
    this.error = '';
    this.success = '';

    if (this.form.invalid) return;

    this.loading = true;
    const { name, email, password } = this.form.getRawValue();
    this.auth.register({ name, email, password }).subscribe({
      next: res => {
        this.loading = false;
        this.success = res.message;
        // this.router.navigateByUrl('/login');
      },
      error: err => {
        this.loading = false;
        this.error = err.message ?? 'Registration failed';
      }
    });
  }
}

