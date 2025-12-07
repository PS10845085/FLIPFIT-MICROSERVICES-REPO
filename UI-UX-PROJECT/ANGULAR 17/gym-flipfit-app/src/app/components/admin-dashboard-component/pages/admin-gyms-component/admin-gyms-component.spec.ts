import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminGymsComponent } from './admin-gyms-component';

describe('AdminGymsComponent', () => {
  let component: AdminGymsComponent;
  let fixture: ComponentFixture<AdminGymsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminGymsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminGymsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
