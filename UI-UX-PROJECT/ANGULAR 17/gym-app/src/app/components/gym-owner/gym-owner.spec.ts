import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymOwner } from './gym-owner';

describe('GymOwner', () => {
  let component: GymOwner;
  let fixture: ComponentFixture<GymOwner>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GymOwner]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GymOwner);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
