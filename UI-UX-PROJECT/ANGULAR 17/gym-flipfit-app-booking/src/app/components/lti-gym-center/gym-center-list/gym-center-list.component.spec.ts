import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymCenterListComponent } from './gym-center-list.component';

describe('GymCenterListComponent', () => {
  let component: GymCenterListComponent;
  let fixture: ComponentFixture<GymCenterListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymCenterListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymCenterListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
