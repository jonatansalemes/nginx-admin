import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ComponentMisc } from '../component-misc';

@Component({
  selector: 'ui-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent implements OnInit {

  @Input() private value:string = "";
  @Input() private type:string = "text";
  @Input() private name:string;
  @Input() private id:string;
  @Input() private required:boolean = false;
  @Input() private cssClass:string = "ui-input";

  constructor() { }

  ngOnInit() {
    if(null == this.name) this.name = ComponentMisc.randomNameFor();
    this.id = ComponentMisc.idFor(this.name,this.id);
  }

  public isValid() : boolean {
    this.cssClass = this.cssClass.replace('is-invalid','');
    if(this.required && !this.value) {
      this.cssClass = this.cssClass + " is-invalid";
      return false;
    } else {
      return true;
    }
  }

}
