import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymCenterComponent } from './gym-center.component';

describe('GymCenterComponent', () => {
  let component: GymCenterComponent;
  let fixture: ComponentFixture<GymCenterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymCenterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymCenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
