import { Component, OnInit, Input, ContentChildren, ViewChild, QueryList, ViewChildren, AfterViewInit, AfterContentInit, forwardRef } from '@angular/core';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { InputComponent } from '../input/input.component';
import { FormGroupComponent } from '../form-group/form-group.component';
import { HtmlElement } from '../html/html-element';

@Component({
  selector: 'ui-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit,AfterContentInit {

  
  @Input() private method:string = "GET";
  @Input() private action:string = "#";
  @ContentChildren(HtmlElement,{descendants:true}) validables: QueryList<HtmlElement>;

  private hasBlankField:boolean = false;
  
  constructor() { }

  ngOnInit() {
    
  }

  ngAfterContentInit(): void {
    this.validables.forEach(input => { 
      console.log(input);
    });
  }

  public onSubmit(event:any) : void {
    event.preventDefault();
    if(!this.hasBlankFields()){
      this.hasBlankField = false;
       console.log('Proceed with submit');
    } else {
      this.hasBlankField = true;
      console.log('has blanks');
    }
  }

  private hasBlankFields() : boolean {
    for(let validable of this.validables.toArray()) {
      if(!validable.isValid()) {
        return true;
      }
    }
    return false;
  }

}
