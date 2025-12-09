import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOwnersComponent } from './admin-owners-component';

describe('AdminOwnersComponent', () => {
  let component: AdminOwnersComponent;
  let fixture: ComponentFixture<AdminOwnersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminOwnersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminOwnersComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
