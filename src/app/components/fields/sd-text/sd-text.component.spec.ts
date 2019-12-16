import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SdTextComponent } from './sd-text.component';

describe('SdTextComponent', () => {
  let component: SdTextComponent;
  let fixture: ComponentFixture<SdTextComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SdTextComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
