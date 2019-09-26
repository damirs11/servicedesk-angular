import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { AlertModalComponent } from '../dialogs/alert-modal/alert-modal.component';
import { ConfirmModalComponent } from '../dialogs/confirm-modal/confirm-modal.component';
import { ImagePopupModalComponent } from '../dialogs/image-popup-modal/image-popup-modal.component';
import { TextModalComponent } from '../dialogs/text-modal/text-modal.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [AlertModalComponent, ConfirmModalComponent, ImagePopupModalComponent, TextModalComponent],
  entryComponents: [AlertModalComponent, ConfirmModalComponent, ImagePopupModalComponent, TextModalComponent],
  imports: [CommonModule, FormsModule, FormsModule, ReactiveFormsModule]
})
export class ModalActionModule {}
