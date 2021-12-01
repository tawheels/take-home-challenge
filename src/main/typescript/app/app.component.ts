import { Component } from '@angular/core';

@Component({
  selector: 'body',
  template: `
  <mat-toolbar>
     <div class="toolbar-title">Movies</div>
     <movie-search></movie-search>
  </mat-toolbar>
  <div class="scroll">
    <router-outlet></router-outlet>
  </div>
  
`,
  styleUrls: ['./movies.scss']
})
export class AppComponent {
  title = 'test';
}
