import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { MainModalComponent } from '../main-modal/main-modal.component';
import { AlertModalComponent } from '../dialogs/alert-modal/alert-modal.component';

@NgModule({
  declarations: [MainModalComponent, AlertModalComponent],
  entryComponents: [AlertModalComponent],
  imports: [CommonModule]
})
export class ModalActionModule {}
