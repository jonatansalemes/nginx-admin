import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'ui-label',
  templateUrl: './label.component.html',
  styleUrls: ['./label.component.css']
})
export class LabelComponent implements OnInit {

  @Input() private forElement:string;

  constructor() { }

  ngOnInit() {
  }

}
