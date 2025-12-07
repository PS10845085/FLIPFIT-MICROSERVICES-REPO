import { Component } from '@angular/core';


import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

interface MenuItem { label: string; icon: string; route: string; }

@Component({
  selector: 'app-admin-dashboard-component',
  standalone: false,
  templateUrl: './admin-dashboard-component.html',
  styleUrl: './admin-dashboard-component.css',
})
export class AdminDashboardComponent {

menu: MenuItem[] = [
    { label: 'Overview', icon: 'dashboard', route: 'overview' },
    { label: 'Gyms',     icon: 'store',     route: 'gyms' },
    { label: 'Users',    icon: 'supervisor_account', route: 'users' },
    { label: 'Settings', icon: 'settings',  route: 'settings' },
    { label: 'Audit Logs', icon: 'fact_check', route: 'audits' }
  ];

  constructor(private auth: AuthService, private router: Router) {}

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

}
