import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { FormComponent } from './form/form.component';
import { FormGroupComponent } from './form-group/form-group.component';
import { InputComponent } from './input/input.component';
import { LabelComponent } from './label/label.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { ButtonComponent } from './button/button.component';


@NgModule({
  declarations: [
    AppComponent,
    LabelComponent,
    FormComponent,
    FormGroupComponent,
    InputComponent,
    ToolbarComponent,
    ButtonComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
