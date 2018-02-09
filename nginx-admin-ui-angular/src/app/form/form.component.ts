import { Component, OnInit, Input, ContentChildren, ViewChild } from '@angular/core';
import { ToolbarComponent } from '../toolbar/toolbar.component';

@Component({
  selector: 'ui-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  @Input() private method:string = "GET";
  @Input() private action:string = "#";
  

  constructor() { }

  ngOnInit() {

  }

  public onSubmit(event:any) : void {
    event.preventDefault();
  }

}
