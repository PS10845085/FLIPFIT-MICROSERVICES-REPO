import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymCustomer } from './gym-customer';

describe('GymCustomer', () => {
  let component: GymCustomer;
  let fixture: ComponentFixture<GymCustomer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GymCustomer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GymCustomer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
