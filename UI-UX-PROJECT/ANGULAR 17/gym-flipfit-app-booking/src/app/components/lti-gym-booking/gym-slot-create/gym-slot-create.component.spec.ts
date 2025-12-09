import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymSlotCreateComponent } from './gym-slot-create.component';

describe('GymSlotCreateComponent', () => {
  let component: GymSlotCreateComponent;
  let fixture: ComponentFixture<GymSlotCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymSlotCreateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymSlotCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
