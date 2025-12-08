
// src/app/app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './components/dashboard-component/dashboard-component';
import { LoginComponent } from './components/login-component/login-component';
import { authGuard } from './guards/auth.guard';  
import { guestGuard } from './guards/guest.guard';

import { roleGuard } from './guards/role.guard';


import { RegisterComponent } from './components/register-component/register-component';

import { OwnerDashboardComponent } from './components/owner-dashboard-component/owner-dashboard-component';
import { AdminDashboardComponent } from './components/admin-dashboard-component/admin-dashboard-component';
import { CustomerDashboardComponent } from './components/customer-dashboard-component/customer-dashboard-component';

// Owner pages
import { OwnerOverviewComponent } from './components/owner-dashboard-component/pages/owner-overview-component/owner-overview-component';
import { OwnerMembersComponent } from './components/owner-dashboard-component/pages/owner-members-component/owner-members-component';
import { OwnerTrainersComponent } from './components/owner-dashboard-component/pages/owner-trainers-component/owner-trainers-component';
import { OwnerClassesComponent } from './components/owner-dashboard-component/pages/owner-classes-component/owner-classes-component';
import { OwnerBillingComponent } from './components/owner-dashboard-component/pages/owner-billing-component/owner-billing-component';

// Admin pages
import { AdminOverviewComponent } from './components/admin-dashboard-component/pages/admin-overview-component/admin-overview-component';
import { AdminGymsComponent } from './components/admin-dashboard-component/pages/admin-gyms-component/admin-gyms-component';
import { AdminCustomersComponent } from './components/admin-dashboard-component/pages/admin-customers-component/admin-customers-component';
import { AdminSettingsComponent } from './components/admin-dashboard-component/pages/admin-settings-component/admin-settings-component';
import { AdminAuditsComponent } from './components/admin-dashboard-component/pages/admin-audits-component/admin-audits-component';

// Customer pages
import { CustomerOverviewComponent } from './components/customer-dashboard-component/pages/customer-overview-component/customer-overview-component';
import { CustomerWorkoutsComponent } from './components/customer-dashboard-component/pages/customer-workouts-component/customer-workouts-component';
import { CustomerSubscriptionsComponent } from './components/customer-dashboard-component/pages/customer-subscriptions-component/customer-subscriptions-component';
import { CustomerProfileComponent } from './components/customer-dashboard-component/pages/customer-profile-component/customer-profile-component';

const routes: Routes = [
  // Public route
  { path: 'login', component: LoginComponent,  canActivate: [guestGuard]},

  { path: 'register', component: RegisterComponent, canActivate: [guestGuard] }, 


  
{ 
    path: 'dashboard-owner', 
    component: OwnerDashboardComponent, 
    canActivate: [authGuard, roleGuard],
    data: { roles: [1] } , // OWNER
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'overview' },
      { path: 'overview', component: OwnerOverviewComponent },
      { path: 'members', component: OwnerMembersComponent },
      { path: 'trainers', component: OwnerTrainersComponent },
      { path: 'classes', component: OwnerClassesComponent },
      { path: 'billing', component: OwnerBillingComponent }
    ]

  },
  { 
    path: 'dashboard-admin', 
    component: AdminDashboardComponent, 
    canActivate: [authGuard, roleGuard],
    data: { roles: [2] } , // ADMIN
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'overview' },
      { path: 'overview', component: AdminOverviewComponent },
      { path: 'gyms', component: AdminGymsComponent },
      { path: 'users', component: AdminCustomersComponent },
      { path: 'settings', component: AdminSettingsComponent },
      { path: 'audits', component: AdminAuditsComponent }
    ]

  },
  { 
    path: 'dashboard-customer', 
    component: CustomerDashboardComponent, 
    canActivate: [authGuard, roleGuard],
    data: { roles: [3] }, // CUSTOMER
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'overview' },
      { path: 'overview', component: CustomerOverviewComponent },
      { path: 'workouts', component: CustomerWorkoutsComponent },
      { path: 'subscriptions', component: CustomerSubscriptionsComponent },
      { path: 'profile', component: CustomerProfileComponent }
    ]

  },

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
