
// src/app/app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './components/dashboard-component/dashboard-component';
import { LoginComponent } from './components/login-component/login-component';
import { authGuard } from './guards/auth.guard';  
import { guestGuard } from './guards/guest.guard';



const routes: Routes = [
  // Public route
  { path: 'login', component: LoginComponent,  canActivate: [guestGuard]},

  // Protected route
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },

  // Default: go to login
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  // Wildcard: go to login
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    // For Angular Universal (SSR), you can enable:
    // initialNavigation: 'enabledBlocking'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
