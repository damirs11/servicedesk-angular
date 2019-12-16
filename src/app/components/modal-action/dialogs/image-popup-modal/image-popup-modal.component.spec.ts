import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImagePopupModalComponent } from './image-popup-modal.component';

describe('ImagePopupModalComponent', () => {
  let component: ImagePopupModalComponent;
  let fixture: ComponentFixture<ImagePopupModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImagePopupModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImagePopupModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
