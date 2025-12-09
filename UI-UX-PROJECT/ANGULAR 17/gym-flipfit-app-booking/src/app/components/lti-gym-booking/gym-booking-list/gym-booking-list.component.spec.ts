import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymBookingListComponent } from './gym-booking-list.component';

describe('GymBookingListComponent', () => {
  let component: GymBookingListComponent;
  let fixture: ComponentFixture<GymBookingListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymBookingListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymBookingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
