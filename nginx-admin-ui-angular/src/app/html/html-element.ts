import { Input } from "@angular/core";

export abstract class HtmlElement {

    @Input() protected cssClass:string = "";

    public hasClass(cssClass:string) : boolean {
        return this.cssClass.indexOf(cssClass) > -1;
    }
    public addClass(cssClass:string) : void {
        this.cssClass = this.cssClass.concat(' ').concat(cssClass);
    }
    public removeClass(cssClass:string) : void {
        this.cssClass = this.cssClass.replace(cssClass,'');
    }

    public abstract isValid() : boolean;
}