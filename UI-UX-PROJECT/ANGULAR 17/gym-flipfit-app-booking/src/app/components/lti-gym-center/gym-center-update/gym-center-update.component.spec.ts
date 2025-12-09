import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymCenterUpdateComponent } from './gym-center-update.component';

describe('GymCenterUpdateComponent', () => {
  let component: GymCenterUpdateComponent;
  let fixture: ComponentFixture<GymCenterUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymCenterUpdateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymCenterUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
