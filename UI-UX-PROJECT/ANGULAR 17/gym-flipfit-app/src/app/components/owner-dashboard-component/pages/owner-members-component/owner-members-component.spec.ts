import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerMembersComponent } from './owner-members-component';

describe('OwnerMembersComponent', () => {
  let component: OwnerMembersComponent;
  let fixture: ComponentFixture<OwnerMembersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OwnerMembersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerMembersComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
