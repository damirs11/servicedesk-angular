import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SdDropdownComponent } from './sd-dropdown.component';

describe('SdDropdownComponent', () => {
  let component: SdDropdownComponent;
  let fixture: ComponentFixture<SdDropdownComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SdDropdownComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
