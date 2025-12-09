import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GymCenterComponent } from './components/gym-center/gym-center.component';
import { GymCenterListComponent } from './components/lti-gym-center/gym-center-list/gym-center-list.component';
import { GymCenterCreateComponent } from './components/lti-gym-center/gym-center-create/gym-center-create.component';
import { GymCenterUpdateComponent } from './components/lti-gym-center/gym-center-update/gym-center-update.component';
import { CustomerComponent } from './components/customer/customer.component';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { UserserviceService } from './services/userservice.service';
import { UserComponent } from './components/user/user.component';
import { GymSlotListComponent } from './components/lti-gym-booking/gym-slot-list/gym-slot-list.component';
import { GymSlotCreateComponent } from './components/lti-gym-booking/gym-slot-create/gym-slot-create.component';
import { CommonModule } from '@angular/common';
import { AnalysisComponent } from './components/analysis/analysis.component';
import { ChartModule } from 'primeng/chart';
import { GymSchedulerCreateComponent } from './components/lti-gym-booking/gym-scheduler-create/gym-scheduler-create.component';
import { GymSchedulerListComponent } from './components/lti-gym-booking/gym-scheduler-list/gym-scheduler-list.component';
import { GymBookingListComponent } from './components/lti-gym-booking/gym-booking-list/gym-booking-list.component';
import { GymBookingCreateComponent } from './components/lti-gym-booking/gym-booking-create/gym-booking-create.component';

@NgModule({
  declarations: [
    AppComponent,
    GymCenterComponent,
    GymCenterListComponent,
    GymCenterCreateComponent,
    GymCenterUpdateComponent,
    CustomerComponent,
    UserComponent,
    GymSlotListComponent,
    GymSlotCreateComponent,
    AnalysisComponent,
    GymSchedulerCreateComponent,
    GymSchedulerListComponent,
    GymBookingListComponent,
    GymBookingCreateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    ChartModule,
    HttpClientModule
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch()),
    UserserviceService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
