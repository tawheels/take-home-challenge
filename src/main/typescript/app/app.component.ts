import { Component } from '@angular/core';

@Component({
  selector: 'body',
  template: `
  <mat-toolbar>
     <div class="toolbar-title">Take Home Challenge</div>
     <nav mat-tab-nav-bar>
      <a mat-tab-link routerLink="/people" routerLineActive="active">People</a>
      <a mat-tab-link routerLink="/movies" routerLineActive="active" >Movies</a>
    </nav>

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
