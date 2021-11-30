import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
  <mat-toolbar>
     <span>Movies</span>
     <movie-search></movie-search>
  </mat-toolbar>
  <router-outlet></router-outlet>
  
`,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'test';
}
