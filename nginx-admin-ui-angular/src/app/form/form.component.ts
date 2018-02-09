import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'ui-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  private method:string = "GET";
  private action:string = "#";

  constructor() { }

  ngOnInit() {

  }

}
