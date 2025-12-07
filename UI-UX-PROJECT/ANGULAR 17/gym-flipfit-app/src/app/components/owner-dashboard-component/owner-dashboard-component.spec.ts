import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerDashboardComponent } from './owner-dashboard-component';

describe('OwnerDashboardComponent', () => {
  let component: OwnerDashboardComponent;
  let fixture: ComponentFixture<OwnerDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OwnerDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerDashboardComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
