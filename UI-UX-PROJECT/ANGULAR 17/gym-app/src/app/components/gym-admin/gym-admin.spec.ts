import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymAdmin } from './gym-admin';

describe('GymAdmin', () => {
  let component: GymAdmin;
  let fixture: ComponentFixture<GymAdmin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GymAdmin]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GymAdmin);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
