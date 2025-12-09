
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CenterRequest } from '../../../models/gym-center/center-request';
import { GymCenterService } from '../../../services/center/gym-center.service';
import { CenterResponse } from '../../../models/gym-center/center-response';

@Component({
  selector: 'app-gym-center-update',
  templateUrl: './gym-center-update.component.html',
  styleUrls: ['./gym-center-update.component.css']
})
export class GymCenterUpdateComponent implements OnInit {
  centerId = 1;
  response?: CenterResponse;
  //center = { name: '', emailId: '', phoneNo: '', status: 'INACTIVE' };
   // Example payload matching the request DTO (with slot + address)
    center: CenterRequest = {
      name: 'PowerHouse Fitness',
      emailId: 'info@powerhousefit.com',
      phoneNo: '9123456789',
      ownerId: 202,
      status: 'INACTIVE',
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

  constructor(private route: ActivatedRoute, private gymCenterService: GymCenterService) {}

  ngOnInit() {
    //this.centerId = Number(this.route.snapshot.paramMap.get('id'));
    // Fetch center details from API and populate `center`
    console.log('Editing center with ID:', this.centerId);
  }

  
updateCenter(): void {
    console.log('Updated center:',JSON.stringify( this.center), this.centerId);

    this.gymCenterService.updateCenter(this.centerId, this.center).subscribe({
      next: (res) => {
        alert('Center updated successfully!');
        this.response = res;
        console.log(res);
      },
      error: (err) => {
        alert('Error updating center');
        console.error(err);
      }
    });
  }

}
