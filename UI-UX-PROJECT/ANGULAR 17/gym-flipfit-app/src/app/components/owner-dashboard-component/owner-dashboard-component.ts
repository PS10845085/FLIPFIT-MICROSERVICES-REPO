import { Component } from '@angular/core';

import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';


interface MenuItem {
  label: string;
  icon: string;
  route: string; // child route
}


@Component({
  selector: 'app-owner-dashboard-component',
  standalone: false,
  templateUrl: './owner-dashboard-component.html',
  styleUrl: './owner-dashboard-component.css',
})
export class OwnerDashboardComponent {

 menu: MenuItem[] = [
    { label: 'Overview',  icon: 'dashboard',          route: 'overview' },
    { label: 'Members',   icon: 'group',              route: 'members' },
    { label: 'Trainers',  icon: 'sports_martial_arts',route: 'trainers' },
    { label: 'Classes',   icon: 'event',              route: 'classes' },
    { label: 'Billing',   icon: 'request_quote',      route: 'billing' }
  ];

  displayName = 'Owner';

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
      const u = this.auth.currentUser();
      // Prefer username; fall back gracefully
      this.displayName = u?.username
        ? u.username
        : 'Owner';
    }
  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

}
