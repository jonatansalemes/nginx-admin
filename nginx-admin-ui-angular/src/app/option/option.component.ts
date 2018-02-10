import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'ui-option',
  templateUrl: './option.component.html',
  styleUrls: ['./option.component.css']
})
export class OptionComponent implements OnInit {

  @Input() private value:string;
  @Input() private text:string;

  constructor() { }

  ngOnInit() {

  }

}
