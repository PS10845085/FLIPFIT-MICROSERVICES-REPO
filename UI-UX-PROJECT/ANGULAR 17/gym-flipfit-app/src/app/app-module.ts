import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { LoginComponent } from './components/login-component/login-component';

// Angular Material (optional - include only what you use)
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MatGridListModule} from '@angular/material/grid-list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';


import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';


import { MatSidenavModule } from '@angular/material/sidenav';  
import { MatListModule } from '@angular/material/list';       

import { MatDividerModule } from '@angular/material/divider'; 


// Forms
import { ReactiveFormsModule } from '@angular/forms'

import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { authInterceptor} from './interceptors/auth.interceptor';
import { DashboardComponent } from './components/dashboard-component/dashboard-component';
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
import { AdminUsersComponent } from './components/admin-dashboard-component/pages/admin-users-component/admin-users-component';
import { AdminSettingsComponent } from './components/admin-dashboard-component/pages/admin-settings-component/admin-settings-component';
import { AdminAuditsComponent } from './components/admin-dashboard-component/pages/admin-audits-component/admin-audits-component';

// Customer pages
import { CustomerOverviewComponent } from './components/customer-dashboard-component/pages/customer-overview-component/customer-overview-component';
import { CustomerWorkoutsComponent } from './components/customer-dashboard-component/pages/customer-workouts-component/customer-workouts-component';
import { CustomerSubscriptionsComponent } from './components/customer-dashboard-component/pages/customer-subscriptions-component/customer-subscriptions-component';
import { CustomerProfileComponent } from './components/customer-dashboard-component/pages/customer-profile-component/customer-profile-component';


@NgModule({
  declarations: [
    App,
    LoginComponent,
    DashboardComponent,
    RegisterComponent,
    OwnerDashboardComponent,
    AdminDashboardComponent,
    CustomerDashboardComponent,

    OwnerOverviewComponent,
    OwnerMembersComponent,
    OwnerTrainersComponent,
    OwnerClassesComponent,
    OwnerBillingComponent,

    AdminOverviewComponent,
    AdminGymsComponent,
    AdminUsersComponent,
    AdminSettingsComponent,
    AdminAuditsComponent,
    
    CustomerOverviewComponent,
    CustomerWorkoutsComponent,
    CustomerSubscriptionsComponent,
    CustomerProfileComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
// Required for [formGroup], formControlName, etc.
    ReactiveFormsModule,

    
 // Material modules (remove if not using Material)
    MatButtonModule,
    MatFormFieldModule,
    MatGridListModule,
    MatToolbarModule,
    MatInputModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatTableModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatOptionModule,
    MatDividerModule,
    MatSidenavModule, 
    MatListModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule


  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideClientHydration(withEventReplay()),
    provideHttpClient(withInterceptors([authInterceptor]))
  ],
  bootstrap: [App]
})
export class AppModule { }
