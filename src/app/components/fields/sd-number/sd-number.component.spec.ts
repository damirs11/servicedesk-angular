import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SdNumberComponent } from './sd-number.component';

describe('SdNumberComponent', () => {
  let component: SdNumberComponent;
  let fixture: ComponentFixture<SdNumberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SdNumberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdNumberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
