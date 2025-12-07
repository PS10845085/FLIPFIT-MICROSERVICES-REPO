import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerWorkoutsComponent } from './customer-workouts-component';

describe('CustomerWorkoutsComponent', () => {
  let component: CustomerWorkoutsComponent;
  let fixture: ComponentFixture<CustomerWorkoutsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomerWorkoutsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerWorkoutsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
