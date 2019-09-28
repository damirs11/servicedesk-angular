import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { EntityModule } from "src/api/entity.module";
import { ModalActionModule } from "./components/modal-action/modal-action.module";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { FormsModule } from "@angular/forms";
import { MainComponent } from "./forms/main/main.component";
import { SdTextComponent } from './components/fields/sd-text/sd-text.component';
import { MainModalComponent } from './test-components/main-modal/main-modal.component';
import { FieldsComponent } from './test-components/fields/fields.component';

@NgModule({
  declarations: [AppComponent, MainComponent, 
                  MainModalComponent, FieldsComponent,
                  SdTextComponent],
  imports: [BrowserModule, AppRoutingModule, FormsModule, NgbModule, 
            EntityModule, ModalActionModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
