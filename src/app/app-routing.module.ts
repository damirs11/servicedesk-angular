import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AppComponent } from "./app.component";
import { MainModalComponent } from './test-components/main-modal/main-modal.component';
import { FieldsComponent } from './test-components/fields/fields.component';

const routes: Routes = [
  { path: "", component: AppComponent },
  { path: "test/dialogs", component: MainModalComponent },
  { path: "test/fields", component: FieldsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
