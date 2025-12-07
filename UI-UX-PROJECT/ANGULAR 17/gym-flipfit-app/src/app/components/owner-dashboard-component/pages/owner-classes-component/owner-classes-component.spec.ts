import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerClassesComponent } from './owner-classes-component';

describe('OwnerClassesComponent', () => {
  let component: OwnerClassesComponent;
  let fixture: ComponentFixture<OwnerClassesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OwnerClassesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerClassesComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
