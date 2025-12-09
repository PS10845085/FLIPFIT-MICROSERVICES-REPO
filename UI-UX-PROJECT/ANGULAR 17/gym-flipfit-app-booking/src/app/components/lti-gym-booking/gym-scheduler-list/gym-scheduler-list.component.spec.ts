import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymSchedulerListComponent } from './gym-scheduler-list.component';

describe('GymSchedulerListComponent', () => {
  let component: GymSchedulerListComponent;
  let fixture: ComponentFixture<GymSchedulerListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymSchedulerListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymSchedulerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
