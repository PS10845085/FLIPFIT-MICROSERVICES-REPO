import { TestBed } from '@angular/core/testing';

import { GymSlotService } from './gym-slot.service';

describe('GymSlotService', () => {
  let service: GymSlotService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GymSlotService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
