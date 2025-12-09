import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymBookingCreateComponent } from './gym-booking-create.component';

describe('GymBookingCreateComponent', () => {
  let component: GymBookingCreateComponent;
  let fixture: ComponentFixture<GymBookingCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymBookingCreateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymBookingCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
