import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SdDatetimeComponent } from './sd-datetime.component';

describe('SdDatetimeComponent', () => {
  let component: SdDatetimeComponent;
  let fixture: ComponentFixture<SdDatetimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SdDatetimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdDatetimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
