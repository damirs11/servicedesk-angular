import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AppComponent } from "./app.component";
import { MainModalComponent } from './test-components/main-modal/main-modal.component';

const routes: Routes = [
  { path: "", component: AppComponent },
  { path: "test/dialog", component: MainModalComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
