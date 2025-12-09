import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GymCenterComponent } from './components/gym-center/gym-center.component';
import { GymCenterListComponent } from './components/lti-gym-center/gym-center-list/gym-center-list.component';
import { GymCenterUpdateComponent } from './components/lti-gym-center/gym-center-update/gym-center-update.component';
import { GymCenterCreateComponent } from './components/lti-gym-center/gym-center-create/gym-center-create.component';
import { GymSlotListComponent } from './components/lti-gym-booking/gym-slot-list/gym-slot-list.component';
import { GymSlotCreateComponent } from './components/lti-gym-booking/gym-slot-create/gym-slot-create.component';
import { AnalysisComponent } from './components/analysis/analysis.component';
import { GymSchedulerListComponent } from './components/lti-gym-booking/gym-scheduler-list/gym-scheduler-list.component';
import { GymSchedulerCreateComponent } from './components/lti-gym-booking/gym-scheduler-create/gym-scheduler-create.component';
import { GymBookingListComponent } from './components/lti-gym-booking/gym-booking-list/gym-booking-list.component';
import { GymBookingCreateComponent } from './components/lti-gym-booking/gym-booking-create/gym-booking-create.component';


const routes: Routes = [

  { path: 'create-center', component: GymCenterCreateComponent },
  { path: 'update-center', component: GymCenterUpdateComponent },
  { path: 'centers-list', component: GymCenterListComponent },
  { path: 'slot-list', component: GymSlotListComponent },
  { path: 'create-slot', component: GymSlotCreateComponent }, // for create
  { path: 'create-slot/:id', component: GymSlotCreateComponent }, // for update 
  { path: 'create-schedule', component: GymSchedulerCreateComponent }, // for create schedule
  { path: 'create-schedule/:id', component: GymSchedulerCreateComponent }, // edit mode via :id + state
  { path: 'schedule-list', component: GymSchedulerListComponent},
  { path: 'create-booking', component: GymBookingCreateComponent }, 
  { path: 'bookings-list', component: GymBookingListComponent},
  { path: 'analysis', component: AnalysisComponent },
  { path: '', redirectTo: '/list-centers', pathMatch: 'full' }, // default route
  { path: '**', redirectTo: '/list-centers' }

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
