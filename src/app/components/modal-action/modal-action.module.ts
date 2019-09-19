import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { NgxSmartModalModule } from 'ngx-smart-modal';
import { MainModalComponent } from '../main-modal/main-modal.component';

@NgModule({
  declarations: [MainModalComponent],
  imports: [CommonModule, NgxSmartModalModule.forChild()]
})
export class ModalActionModule {}
