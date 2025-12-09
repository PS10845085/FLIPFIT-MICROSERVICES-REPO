
// components/booking/gym-booking-create/gym-booking-create.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GymBookingService } from '../../../services/booking/gym-booking.service';
import { GymBookingResponse } from '../../../models/gym-booking/gym-booking-response';

@Component({
  selector: 'app-gym-booking-create',
  templateUrl: './gym-booking-create.component.html',
  styleUrls: ['./gym-booking-create.component.css'] 
})
export class GymBookingCreateComponent implements OnInit {
  bookingForm!: FormGroup;
  loading = false;
  // Optional: capture created booking for display/logging
  createdBooking?: GymBookingResponse;

  constructor(
    private fb: FormBuilder,
    private bookingService: GymBookingService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Build reactive form (userId & scheduleId are required, numeric, positive)
    this.bookingForm = this.fb.group({
      userId: [
        null,
        [Validators.required, Validators.min(1), this.integerValidator]
      ],
      scheduleId: [
        null,
        [Validators.required, Validators.min(1), this.integerValidator]
      ]
    });

    // Hydrate from router state or query params (optional convenience)
    const nav = this.router.getCurrentNavigation();
    const stateUserId = nav?.extras?.state?.['userId'];
    const stateScheduleId = nav?.extras?.state?.['scheduleId'];

    const qpUserId = this.route.snapshot.queryParamMap.get('userId');
    const qpScheduleId = this.route.snapshot.queryParamMap.get('scheduleId');

    this.bookingForm.patchValue({
      userId: coerceNumber(stateUserId ?? qpUserId),
      scheduleId: coerceNumber(stateScheduleId ?? qpScheduleId)
    });
  }

  /** Custom validator to ensure integers (not floats or strings) */
  private integerValidator(control: AbstractControl) {
    const v = control.value;
    if (v == null || v === '') return null;
    const n = Number(v);
    if (!Number.isFinite(n) || !Number.isInteger(n)) {
      return { integer: true };
    }
    return null;
  }

  /** Simple helper to expose form controls in code (optional) */
  get f() {
    return this.bookingForm.controls;
  }

  /** Submit handler: creates a booking */
  onSubmit(): void {
    if (this.bookingForm.invalid) {
      alert('Please provide valid User ID and Schedule ID.');
      markFormGroupTouched(this.bookingForm);
      return;
    }

    const userId = Number(this.f['userId'].value);
    const scheduleId = Number(this.f['scheduleId'].value);

    this.loading = true;

    this.bookingService.createBooking(userId, scheduleId).subscribe({
      next: (booking) => {
        this.createdBooking = booking;
        this.loading = false;

        // Success UX (toast/alert/logging)
        alert(`Booking #${booking.id} created for user #${booking.user.id} on schedule #${booking.schedule.id}.`);

        // Optional: reset or navigate
        this.bookingForm.reset();
        // this.router.navigate(['/bookings'], { queryParams: { userId } });
      },
      error: (err) => {
        console.error('Error creating booking:', err);
        this.loading = false;

        // Surface backend message, if present
        let msg = 'Error creating booking.';
        try {
          const body = typeof err.error === 'string' ? JSON.parse(err.error) : err.error;
          msg = body?.message || msg;
        } catch { /* ignore parse errors */ }
        alert(msg);
      }
    });
  }
}

/** Helpers */

function coerceNumber(value: unknown): number | null {
  if (value == null) return null;
  const n = Number(value);
  return Number.isFinite(n) ? n : null;
}

function markFormGroupTouched(form: FormGroup) {
  Object.values(form.controls).forEach(control => {
    control.markAsTouched();
    control.updateValueAndValidity({ onlySelf: true });
  });
}

