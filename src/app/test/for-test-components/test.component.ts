import { Component, OnInit } from '@angular/core';
import { DialogService } from 'src/app/components/dialog.service';
import { ExampleComponent } from 'src/app/components/dialogs/example/example.component';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent {

  constructor(public dialog: DialogService) { 
  }

  public openExampleDialog() {
    const ref = this.dialog.open(ExampleComponent, {
      data: { message: 'message message message' }
    });

    ref.afterClosed.subscribe(result => {
      console.log('Dialog closed', result);
    });
  }
}
