import { TestBed } from '@angular/core/testing';

import { GymCenterService } from './center/gym-center.service';

describe('GymCenterService', () => {
  let service: GymCenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GymCenterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
