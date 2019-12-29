import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SdTextareaComponent } from './sd-textarea.component';

describe('SdTextareaComponent', () => {
  let component: SdTextareaComponent;
  let fixture: ComponentFixture<SdTextareaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SdTextareaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdTextareaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
