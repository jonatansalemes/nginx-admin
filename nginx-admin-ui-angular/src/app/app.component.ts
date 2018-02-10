import { Component } from '@angular/core';
import { Server } from './model/server';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
 
  servers = [
    new Server("ip1","alias1"),
    new Server("ip2","alias2")
];


  recall() : void {
    this.servers = [
      new Server("ip3","alias3"),
      new Server("ip4","alias4")
    ];
  }


}
