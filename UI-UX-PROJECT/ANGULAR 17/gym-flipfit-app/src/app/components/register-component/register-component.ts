
// src/app/components/register-component/register-component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { AuthService, RegisterPayload } from '../../services/auth.service';
import { Router } from '@angular/router';


import { RoleService, Role } from '../../services/role.service';
import { CenterService, Center } from '../../services/center.service';

@Component({
  selector: 'app-register',
  templateUrl: './register-component.html',
  styleUrls: ['./register-component.css'],
    standalone: false,

})
export class RegisterComponent implements OnInit {
  submitting = false;
  serverMessage: string | null = null;
  serverError: string | null = null;

  
  roles: Role[] = [];
  rolesLoading = false;
  rolesError: string | null = null;

  
  centers: Center[] = [];
  centersLoading = false;
  centersError: string | null = null;


  // Countdown seconds for redirect
  redirectIn = 0;

  // You no longer need status options since status is removed from the form
  // readonly statusOptions = ['PENDING', 'ACTIVE', 'INACTIVE'];

  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private roleService: RoleService,
    private centerService: CenterService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      account: this.fb.group({
        username: ['', [Validators.required, Validators.minLength(3)]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        roleid: [{ value: null, disabled: true }, [Validators.required]],

        centerid: [{ value: null, disabled: true }, [Validators.required]],
        // status removed
      }),
      profile: this.fb.group({
        firstname: ['', [Validators.required]],
        lastname: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        mobile: [
          null,
          [Validators.required, Validators.pattern(/^\d{10,15}$/)]
        ]
      }),
      address: this.fb.group({
        line1: ['', [Validators.required]],
        line2: [''],
        city: ['', [Validators.required]],
        state: ['', [Validators.required]],
        postalcode: ['', [Validators.required, Validators.pattern(/^\d{4,10}$/)]],
        country: ['', [Validators.required]]
      })
    });
    
    this.loadRoles();

    this.loadCenters();

  }


private loadCenters(): void {
    this.centersLoading = true;
    this.centersError = null;

    const centerCtrl = this.form.get('account.centerid')!;

    this.centerService.getActiveCenters().subscribe({
      next: (data) => {
        this.centers = data ?? [];
        this.centersLoading = false;

        if (this.centers.length > 0) {
          centerCtrl.enable({ emitEvent: false });  // enable selection
          // Optional: preselect first ACTIVE center
          const defaultCenter = this.centers.find(c => c.status === 'ACTIVE') ?? this.centers[0];
          if (defaultCenter) {
            centerCtrl.setValue(defaultCenter.id, { emitEvent: false });
          }
        } else {
          centerCtrl.disable({ emitEvent: false });
        }
      },
      error: (err) => {
        this.centersLoading = false;
        this.centersError = err.message ?? 'Unable to load centers';
        centerCtrl.disable({ emitEvent: false });
      }
    });
  }

private loadRoles(): void {
  this.rolesLoading = true;
  this.rolesError = null;

  this.roleService.getRoles().subscribe({
    next: (data) => {
      this.roles = data ?? [];
      this.rolesLoading = false;

      const roleCtrl = this.form.get('account.roleid')!;
      if (this.roles.length > 0) {
        // Enable the control so the user can select
        roleCtrl.enable({ emitEvent: false });

        // Optional: preselect a default role
        const defaultRole = this.roles.find(r => r.roleName === 'CUSTOMER');
        if (defaultRole) {
          roleCtrl.setValue(defaultRole.roleId, { emitEvent: false });
        }
      } else {
        // No roles returned—keep disabled
        roleCtrl.disable({ emitEvent: false });
      }
    },
    error: (err) => {
      this.rolesLoading = false;
      this.rolesError = err.message || 'Unable to load roles';
      // On error, keep the control disabled
      this.form.get('account.roleid')!.disable({ emitEvent: false });
    }
  });
}

  submit(): void {
    this.serverMessage = null;
    this.serverError = null;

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const account = this.form.get('account') as FormGroup;
    const profile = this.form.get('profile') as FormGroup;
    const address = this.form.get('address') as FormGroup;

    const payload: RegisterPayload = {
      username: account.get('username')!.value,
      password: account.get('password')!.value,
      roleid: Number(account.get('roleid')!.value),
      centerid: Number(account.get('centerid')!.value),
      // Set default value here before calling API
      status: 'PENDING',

      firstname: profile.get('firstname')!.value,
      lastname: profile.get('lastname')!.value,
      email: profile.get('email')!.value,
      mobile: Number(profile.get('mobile')!.value),

      line1: address.get('line1')!.value,
      line2: address.get('line2')!.value,
      city: address.get('city')!.value,
      state: address.get('state')!.value,
      postalcode: address.get('postalcode')!.value,
      country: address.get('country')!.value
    };

    this.submitting = true;

    this.auth.register(payload).subscribe({
      next: (res) => {
        this.submitting = false;
        if (res.status === 'SUCCESS') {
          // Show success and start 5-sec countdown
          this.serverMessage = res.message || 'Registration successful';
          this.redirectIn = 5;

          const interval = setInterval(() => {
            this.redirectIn--;
            if (this.redirectIn <= 0) {
              clearInterval(interval);
              this.router.navigate(['/login'], {
                queryParams: { registered: 'true', username: payload.username }
              });
            }
          }, 1000);
        } else {
          this.serverError = res.message || 'Registration failed';
        }
      },
      error: (err) => {
        this.submitting = false;
        this.serverError = err.message || 'Registration failed';
      }
    });
  }
}
