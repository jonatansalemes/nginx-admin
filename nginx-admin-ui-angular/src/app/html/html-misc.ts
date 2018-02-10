
import { v4 as uuid } from 'uuid';

export class HtmlMisc {

    public static idFor (name:string,id:string) : string {
        if(id) {
            return id;
        } else if(name) {
            return "par_" + name;
        } else {
            return uuid();
        }
    }

    public static randomNameFor() : string {
        return uuid();
    }

    public static id (id:string) : string {
        return this.idFor(null,id);
    }
    
}
