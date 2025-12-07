import { Component, signal } from '@angular/core';

import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class App {

  user: { username?: string; userId?: number; token?: string } = {};
  constructor(public auth: AuthService, private router: Router) {}

  protected readonly title = signal('LTI GYM FLIPFIT APP');
    ngOnInit(): void {
    this.user = this.auth.currentUser() ?? {}; // default to empty object
    
  }

  
  logout(): void {
    
    console.log('[Dashboard] logout clicked');
    this.auth.logout();
    this.router.navigateByUrl('/login');

  }
}
