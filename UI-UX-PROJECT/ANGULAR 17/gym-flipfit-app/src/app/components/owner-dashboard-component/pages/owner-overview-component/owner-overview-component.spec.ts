import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerOverviewComponent } from './owner-overview-component';

describe('OwnerOverviewComponent', () => {
  let component: OwnerOverviewComponent;
  let fixture: ComponentFixture<OwnerOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OwnerOverviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerOverviewComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
