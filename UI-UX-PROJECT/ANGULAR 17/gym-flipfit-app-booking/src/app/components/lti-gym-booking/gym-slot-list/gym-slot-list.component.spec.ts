import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymSlotListComponent } from './gym-slot-list.component';

describe('GymSlotListComponent', () => {
  let component: GymSlotListComponent;
  let fixture: ComponentFixture<GymSlotListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymSlotListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymSlotListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
