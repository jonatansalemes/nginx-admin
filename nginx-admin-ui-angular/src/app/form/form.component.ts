import { Component, OnInit, Input, ContentChildren, ViewChild, QueryList, ViewChildren, AfterViewInit, AfterContentInit, forwardRef } from '@angular/core';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { InputComponent } from '../input/input.component';
import { FormGroupComponent } from '../form-group/form-group.component';

@Component({
  selector: 'ui-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  @Input() private method:string = "GET";
  @Input() private action:string = "#";
  private hasEmptyInputs:boolean = false;
  @ContentChildren(InputComponent, {descendants: true}) inputs: QueryList<InputComponent>;
  
  constructor() { }

  ngOnInit() {
    
  }

  public onSubmit(event:any) : void {
    event.preventDefault();
    if(this.hasBlankFields()){
       this.hasEmptyInputs = true;
    } else {
      this.hasEmptyInputs = false;
    }
  }

  private hasBlankFields() : boolean {
    var hasBlank:boolean = false;
    this.inputs.forEach(input => { 
      hasBlank = input.isValid();
    });
    console.log('has blanks '+hasBlank);
    return hasBlank;
  }

}
