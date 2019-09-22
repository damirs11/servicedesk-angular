import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { HttpClientModule } from "@angular/common/http";
import { EntityModule } from "src/api/entity.module";
import { ModalActionModule } from './components/modal-action/modal-action.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, AppRoutingModule, EntityModule, ModalActionModule, NgbModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
