import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymUser } from './gym-user';

describe('GymUser', () => {
  let component: GymUser;
  let fixture: ComponentFixture<GymUser>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GymUser]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GymUser);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
