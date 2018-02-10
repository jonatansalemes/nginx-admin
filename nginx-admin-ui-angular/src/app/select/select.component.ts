import { Component, OnInit, Input } from '@angular/core';
import { HtmlElement } from '../html/html-element';

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.css']
})
export class SelectComponent extends HtmlElement implements OnInit {

  @Input() private required:boolean = false;

  constructor() { 
    super();
  }

  ngOnInit() {
  }

  isValid() : boolean {
    this.removeClass('is-invalid');
    if(this.required) {
      this.addClass('is-invalid');
      return false;
    } else {
      return true;
    }
  }

}
