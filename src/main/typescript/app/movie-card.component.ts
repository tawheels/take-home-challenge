import { Component, Input } from '@angular/core';

import { Movie } from './service/movies.service';

@Component({
  selector: 'movie-card',
  template: `
    <mat-card>
      <mat-card-header>
        <img mat-card-avatar [src]="movie.img"/>
        <mat-card-title>{{movie.name}}</mat-card-title>  
        <mat-card-subtitle>{{movie.release}}</mat-card-subtitle>
      </mat-card-header>
    </mat-card>
  
`,
  styleUrls: ['./movies.scss']
})
export class MovieCardComponent {
  @Input() movie!: Movie;
}
