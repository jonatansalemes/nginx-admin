import { Component, OnInit, Input, ElementRef } from '@angular/core';

@Component({
  selector: 'ui-option',
  templateUrl: './option.component.html',
  styleUrls: ['./option.component.css']
})
export class OptionComponent implements OnInit {

  @Input() private value:string;

  constructor(private _element: ElementRef) { 

  }

  ngOnInit() {

  }

  _getHostElement(): HTMLElement {
    return this._element.nativeElement;
  }

  get viewValue(): string {
    return (this._getHostElement().textContent || '').trim();
  }

}
