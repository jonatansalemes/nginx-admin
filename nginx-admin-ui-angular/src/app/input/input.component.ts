import { Component, OnInit, Input } from '@angular/core';
import { ComponentMisc } from '../component-misc';

@Component({
  selector: 'ui-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent implements OnInit {


  @Input() private type:string = "text";
  @Input() private name:string;
  @Input() private id:string;

  constructor() { }

  ngOnInit() {
    this.id = ComponentMisc.idFor(this.name,this.id);
  }

}
