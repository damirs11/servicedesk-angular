import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ExampleComponent } from './components/dialogs/example/example.component';
import { DialogModule } from './components/modal-action/dialog.module';
import { TestComponent } from './test/for-test-components/test.component';

@NgModule({
  declarations: [
    AppComponent,
    ExampleComponent,
    TestComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    DialogModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [ExampleComponent]
})
export class AppModule { }
