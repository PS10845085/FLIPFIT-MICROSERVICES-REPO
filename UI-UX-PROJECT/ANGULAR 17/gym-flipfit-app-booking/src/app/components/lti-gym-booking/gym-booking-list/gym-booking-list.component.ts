import { Component } from '@angular/core';
import { GymBookingService } from '../../../services/booking/gym-booking.service';
import { GymBookingResponse } from '../../../models/gym-booking/gym-booking-response';

@Component({
  selector: 'app-gym-booking-list',
  templateUrl: './gym-booking-list.component.html',
  styleUrl: './gym-booking-list.component.css'
})
export class GymBookingListComponent {

bookings: GymBookingResponse[] = [];
  loading = false;
  userId = 2; // Example: hardcoded userId, can be dynamic

  constructor(private bookingService: GymBookingService) {}

  ngOnInit(): void {
    this.fetchBookings();
  }

  fetchBookings(): void {
    this.loading = true;
    this.bookingService.getBookingsByUserId(this.userId).subscribe({
      next: (data) => {
        this.bookings = data || [];
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching bookings:', err);
        this.loading = false;
      }
    });
  }

  formatDate(timestamp: number): string {
    return new Date(timestamp).toLocaleString();
  }

}
