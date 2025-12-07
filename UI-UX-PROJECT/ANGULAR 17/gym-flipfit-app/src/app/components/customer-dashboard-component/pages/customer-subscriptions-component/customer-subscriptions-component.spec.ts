import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerSubscriptionsComponent } from './customer-subscriptions-component';

describe('CustomerSubscriptionsComponent', () => {
  let component: CustomerSubscriptionsComponent;
  let fixture: ComponentFixture<CustomerSubscriptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomerSubscriptionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerSubscriptionsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
