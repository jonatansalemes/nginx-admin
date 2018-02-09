import { Component, OnInit, Input } from '@angular/core';

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

}
