import { TestBed } from '@angular/core/testing';

import { GymSchedulerService } from './gym-scheduler.service';

describe('GymSchedulerService', () => {
  let service: GymSchedulerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GymSchedulerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
