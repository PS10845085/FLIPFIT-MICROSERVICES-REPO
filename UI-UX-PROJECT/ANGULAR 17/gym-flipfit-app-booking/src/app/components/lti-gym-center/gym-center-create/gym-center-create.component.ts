
// src/app/components/gym-create/gym-create.component.ts
import { Component } from '@angular/core';
import { CenterResponse } from '../../../models/gym-center/center-response';
import { CenterRequest } from '../../../models/gym-center/center-request';
import { GymCenterService } from '../../../services/center/gym-center.service';



@Component({
  selector: 'app-gym-center-create',
  templateUrl: './gym-center-create.component.html',
  styleUrls: ['./gym-center-create.component.css']
})
export class GymCenterCreateComponent {
  response?: CenterResponse;

  // Example payload matching the request DTO (with slot + address)
  center: CenterRequest = {
    name: 'PowerHouse Fitness',
    emailId: 'info@powerhousefit.com',
    phoneNo: '9123456789',
    ownerId: 202,
    address: {
      line1: '45 Residency Road',
      line2: 'Opposite City Mall',
      city: 'Mumbai',
      state: 'Maharashtra',
      postalCode: '400001',
      country: 'India'
    },
    slots: [
      {
        startTime: '17:00:00',
        endTime: '18:30:00',
        capacity: 50,
        bookedCount: 18,
        slotDate: '2025-12-01',
        status: 'PARTIALLY_BOOKED'
      }
    ]
  };

  constructor(private gymCenterService: GymCenterService) {}

  createCenter() {
    console.log('Creating center:', this.center);
    this.gymCenterService.createCenter(this.center).subscribe({
      next: (response) => {
        alert('Center created successfully!');
        this.response = response;
        console.log(response);
      },
      error: (err) => {
        alert('Error creating center');
        console.error(err);
      }
    });
  }

   
}
