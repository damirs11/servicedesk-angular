import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { EntityModule } from "src/api/entity.module";
import { ModalActionModule } from "./components/modal-action/modal-action.module";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { FormsModule } from "@angular/forms";
import { MainComponent } from "./forms/main/main.component";
import { SdTextComponent } from "./components/fields/sd-text/sd-text.component";
import { TestModule } from './test-components/test.module';

@NgModule({
  declarations: [AppComponent, MainComponent, SdTextComponent],
  imports: [BrowserModule, AppRoutingModule, NgbModule, FormsModule, 
            EntityModule, ModalActionModule, TestModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
