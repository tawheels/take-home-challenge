import { Component, Input } from '@angular/core';

import { Movie } from './service/movies.service';

@Component({
  selector: 'movie-card',
  template: `
    <mat-card>
      <mat-card-header>
        <img mat-card-avatar [src]="movie.img"/>
        <mat-card-title>{{movie.name}} [{{movie.id}}]</mat-card-title>  
        <mat-card-subtitle>{{movie.release}}</mat-card-subtitle>
      </mat-card-header>
      <mat-list>
        <mat-list-item *ngFor="let person of movie.starring">
          <img mat-list-icon [src]="person.img"/>
          <div mat-line>{{person.name}} [{{person.id}}]</div>
        </mat-list-item>
      </mat-list>
    </mat-card>
  
`,
  styleUrls: ['./movies.scss']
})
export class MovieCardComponent {
  @Input() movie!: Movie;
}
