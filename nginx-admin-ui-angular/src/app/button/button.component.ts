import { Component, OnInit, Input } from '@angular/core';
import { HtmlMisc } from '../html/html-misc';

@Component({
  selector: 'ui-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent implements OnInit {

  @Input() private url:string = "#";
  @Input() private id:string;
  @Input() private state:string = "info";
  @Input() private label:string;
  @Input() private type:string = "button";

  constructor() { }

  ngOnInit() {
    this.id = HtmlMisc.id(this.id);
  }

}
