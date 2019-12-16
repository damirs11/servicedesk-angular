import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IText } from '../../dialog-interfaces/IText';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-text-modal',
  templateUrl: './text-modal.component.html',
  styleUrls: ['./text-modal.component.less']
})
export class TextModalComponent implements OnInit {

  @Input() data: IText;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

  submit() {
    if (!this.canSubmit) { return; }
    this.activeModal.close(this.data.value);
  }

  close() {
    this.activeModal.close(null)
  }

  get canSubmit() {
    if (this.data.value) { return true; }
    if (!this.data.required) { return true; }
    return false;
  }
}
