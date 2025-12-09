import { Component } from '@angular/core';


import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

interface MenuItem { label: string; icon: string; route: string; }

@Component({
  selector: 'app-customer-dashboard-component',
  standalone: false,
  templateUrl: './customer-dashboard-component.html',
  styleUrl: './customer-dashboard-component.css',
})
export class CustomerDashboardComponent {

menu: MenuItem[] = [
    { label: 'Overview',      icon: 'dashboard',        route: 'overview' },
    { label: 'Workouts',      icon: 'fitness_center',   route: 'workouts' },
    { label: 'Subscriptions', icon: 'credit_card',      route: 'subscriptions' },
    { label: 'Profile',       icon: 'person',           route: 'profile' }
  ];

  displayName = 'Customer';

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    const u = this.auth.currentUser();
    // Prefer username; fall back gracefully
    this.displayName = u?.username
      ? u.username
      : 'Customer';
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

}
