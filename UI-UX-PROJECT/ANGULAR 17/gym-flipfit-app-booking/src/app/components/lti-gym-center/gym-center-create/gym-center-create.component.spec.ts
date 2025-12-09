import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymCenterCreateComponent } from './gym-center-create.component';

describe('GymCenterCreateComponent', () => {
  let component: GymCenterCreateComponent;
  let fixture: ComponentFixture<GymCenterCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymCenterCreateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymCenterCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
