import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VsdDetailsComponent } from './vsd-details.component';

describe('VsdDetailsComponent', () => {
  let component: VsdDetailsComponent;
  let fixture: ComponentFixture<VsdDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VsdDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VsdDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
