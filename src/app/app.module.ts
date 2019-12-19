import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { EntityModule } from "src/api/entity.module";
import { ModalActionModule } from "./components/modal-action/modal-action.module";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { FormsModule } from "@angular/forms";
import { ModalComponent } from './test-components/modal/modal.component';
import { FieldsComponent } from './test-components/fields/fields.component';
import { SdTextComponent } from './components/fields/sd-text/sd-text.component';
import { SdTextareaComponent } from './components/fields/sd-textarea/sd-textarea.component';
import { SdNumberComponent } from './components/fields/sd-number/sd-number.component';

@NgModule({
  declarations: [
    AppComponent,
    SdTextComponent,
    SdTextareaComponent,
    FieldsComponent,
    ModalComponent,
    SdTextareaComponent,
    SdNumberComponent
  ],
  imports: [
    BrowserModule, AppRoutingModule, FormsModule, NgbModule,
    EntityModule, ModalActionModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
