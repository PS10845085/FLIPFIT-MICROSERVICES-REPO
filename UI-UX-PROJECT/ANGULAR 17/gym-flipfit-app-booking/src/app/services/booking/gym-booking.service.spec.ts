import { TestBed } from '@angular/core/testing';

import { GymBookingService } from './gym-booking.service';

describe('GymBookingService', () => {
  let service: GymBookingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GymBookingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
