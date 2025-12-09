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
    { label: 'Centers',     icon: 'store',     route: 'gyms' },
    { label: 'Customers',    icon: 'supervisor_account', route: 'users' },
    { label: 'Owners',     icon: 'manage_accounts',       route: 'owners' },
    { label: 'Settings', icon: 'settings',  route: 'settings' },
    { label: 'Audit Logs', icon: 'fact_check', route: 'audits' }
  ];

  displayName = 'Admin';

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    const u = this.auth.currentUser();
    // Prefer username; fall back gracefully
    this.displayName = u?.username
      ? u.username
      : 'Admin';
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

}
