
import { Component } from '@angular/core';

@Component({
  selector: 'app-gym-center',
  templateUrl: './gym-center.component.html',
  styleUrls: ['./gym-center.component.css']
})
export class GymCenterComponent {
  // Sample data for gym centers
  gymCenters = [
    { id: 1, name: 'FitZone Gym', location: 'Bengaluru' },
    { id: 2, name: 'PowerHouse Gym', location: 'Mumbai' },
    { id: 3, name: 'Iron Paradise', location: 'Delhi' }
  ];

  updateCenter(id: number) {
    console.log('Update center with ID:', id);
    // Navigate to update form or open modal
  }

  deleteCenter(id: number) {
    console.log('Delete center with ID:', id);
    this.gymCenters = this.gymCenters.filter(center => center.id !== id);
  }
}
