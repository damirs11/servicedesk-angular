import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { MainModalComponent } from '../main-modal/main-modal.component';
import { AlertModalComponent } from '../dialogs/alert-modal/alert-modal.component';
import { ConfirmModalComponent } from '../dialogs/confirm-modal/confirm-modal.component';
import { ImagePopupModalComponent } from '../dialogs/image-popup-modal/image-popup-modal.component';
import { TextModalComponent } from '../dialogs/text-modal/text-modal.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [MainModalComponent, AlertModalComponent, ConfirmModalComponent, ImagePopupModalComponent, TextModalComponent],
  entryComponents: [AlertModalComponent, ConfirmModalComponent, ImagePopupModalComponent, TextModalComponent],
  imports: [CommonModule, FormsModule]
})
export class ModalActionModule {}
