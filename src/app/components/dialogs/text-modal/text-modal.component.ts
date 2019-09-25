import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Itext } from '../../dialog-interfaces/Itext';

@Component({
  selector: 'app-text-modal',
  templateUrl: './text-modal.component.html',
  styleUrls: ['./text-modal.component.less']
})
export class TextModalComponent implements OnInit {

  @Input() data: Itext;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

  submit() {
    if (!this.canSubmit) return;
    this.activeModal.close(this.data.value);
  }

  close() {
    this.activeModal.close(null)
  }

  get canSubmit() {
    if (this.data.value) return true;
    if (!this.data.required) return true;
    return false;
  }
}
