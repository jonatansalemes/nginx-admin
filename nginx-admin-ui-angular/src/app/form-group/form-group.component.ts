import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'ui-form-group',
  templateUrl: './form-group.component.html',
  styleUrls: ['./form-group.component.css']
})
export class FormGroupComponent implements OnInit {

  @Input() private label:string;
  @Input() private forElement:string;

  constructor() { }

  ngOnInit() {
  }

}
