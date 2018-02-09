import { Component, OnInit } from '@angular/core';
import { v4 } from 'uuid';

@Component({
  selector: 'ui-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent implements OnInit {


  private type:string = "text";
  private name:string;
  private id:string = v4();

  constructor() { }

  ngOnInit() {
  }

}
