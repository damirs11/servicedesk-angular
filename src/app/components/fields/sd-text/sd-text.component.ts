import { Component, OnInit } from '@angular/core';

const COMMIT_DEBOUNCE: number = 250;

@Component({
  selector: 'app-sd-text',
  templateUrl: './sd-text.component.html',
  styleUrls: ['./sd-text.component.less']
})
export class SdTextComponent implements OnInit {

  
  commitTask: any = null;

  constructor() { }

  ngOnInit() {
  }

}
