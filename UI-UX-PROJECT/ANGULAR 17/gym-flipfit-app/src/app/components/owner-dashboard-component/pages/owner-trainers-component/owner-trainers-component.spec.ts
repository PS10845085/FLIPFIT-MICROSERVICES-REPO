import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerTrainersComponent } from './owner-trainers-component';

describe('OwnerTrainersComponent', () => {
  let component: OwnerTrainersComponent;
  let fixture: ComponentFixture<OwnerTrainersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OwnerTrainersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerTrainersComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
