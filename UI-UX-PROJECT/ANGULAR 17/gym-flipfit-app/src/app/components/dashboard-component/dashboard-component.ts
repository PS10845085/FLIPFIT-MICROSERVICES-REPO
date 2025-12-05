import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard-component.html',
  styleUrls: ['./dashboard-component.css'],
  // non-standalone component: do NOT put `imports` here
  standalone: false
})


export class DashboardComponent {
  user: { username?: string; userId?: number; token?: string } = {};

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.user = this.auth.currentUser() ?? {}; // default to empty object
    
  }

  
logout(): void {
  
  console.log('[Dashboard] logout clicked');
  this.auth.logout();
  this.router.navigateByUrl('/login');

}

}


