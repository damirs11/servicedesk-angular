import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { HttpClientModule } from "@angular/common/http";
import { EntityModule } from "src/api/entity.module";
import { ModalActionModule } from './components/modal-action/modal-action.module';
import { NgxSmartModalService } from 'ngx-smart-modal';

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, AppRoutingModule, EntityModule, ModalActionModule],
  providers: [NgxSmartModalService],
  bootstrap: [AppComponent]
})
export class AppModule {}
