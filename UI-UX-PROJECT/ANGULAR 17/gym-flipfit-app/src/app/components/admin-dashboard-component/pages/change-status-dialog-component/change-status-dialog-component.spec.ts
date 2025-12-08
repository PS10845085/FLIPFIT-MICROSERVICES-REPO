import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeStatusDialogComponent } from './change-status-dialog-component';

describe('ChangeStatusDialogComponent', () => {
  let component: ChangeStatusDialogComponent;
  let fixture: ComponentFixture<ChangeStatusDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChangeStatusDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChangeStatusDialogComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
