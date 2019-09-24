import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { EntityModule } from "src/api/entity.module";
import { ModalActionModule } from './components/modal-action/modal-action.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { AlertModalComponent } from './components/dialogs/alert-modal/alert-modal.component';
import { ConfirmModalComponent } from './components/dialogs/confirm-modal/confirm-modal.component';

@NgModule({
  declarations: [AppComponent, ConfirmModalComponent],
  imports: [BrowserModule, AppRoutingModule, EntityModule, ModalActionModule, NgbModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
