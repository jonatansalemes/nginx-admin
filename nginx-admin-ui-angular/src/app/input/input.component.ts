import { Component, OnInit, Input, Output, EventEmitter, forwardRef } from '@angular/core';
import { HtmlMisc } from '../html/html-misc';
import { HtmlElement } from '../html/html-element';

@Component({
  selector: 'ui-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css'],
  providers: [{provide: HtmlElement, useExisting: forwardRef(() => InputComponent)}]
})
export class InputComponent extends HtmlElement implements OnInit {

  @Input() private value:string = "";
  @Input() private type:string = "text";
  @Input() private name:string;
  @Input() private id:string;
  @Input() private required:boolean = false;
  @Input() protected cssClass:string = "ui-input";

  constructor() { 
    super();
  }

  ngOnInit() {
    if(null == this.name) this.name = HtmlMisc.randomNameFor();
    this.id = HtmlMisc.idFor(this.name,this.id);
  }

  isValid() : boolean {
    this.removeClass('is-invalid');
    if(this.required && !this.value) {
      this.addClass('is-invalid');
      return false;
    } else {
      return true;
    }
  }

}
