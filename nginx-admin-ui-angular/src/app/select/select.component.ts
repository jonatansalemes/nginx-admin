import { Component, OnInit, Input, Output, ContentChildren, QueryList, AfterContentInit } from '@angular/core';
import { HtmlElement } from '../html/html-element';
import { OptionComponent } from '../option/option.component';


@Component({
  selector: 'ui-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.css']
})
export class SelectComponent implements OnInit,AfterContentInit {

  ngAfterContentInit(): void {
    this.options.forEach(option=> {
      console.log(option);
    });
  }
  @Input() private value:string;
  @ContentChildren(OptionComponent, { descendants: true }) options: QueryList<OptionComponent>;

  constructor() { 
    
  }

  ngOnInit() {
    
  }

}
