import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymSchedulerCreateComponent } from './gym-scheduler-create.component';

describe('GymSchedulerCreateComponent', () => {
  let component: GymSchedulerCreateComponent;
  let fixture: ComponentFixture<GymSchedulerCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymSchedulerCreateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymSchedulerCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
