import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Movie } from './service/movies.service';

@Component({
  selector: 'movies',
  template: `
    <div class="movies">
      <ng-container *ngIf="movies">
        <movie-card *ngFor="let movie of movies" [movie]="movie"></movie-card>
      </ng-container>
    </div>
  
`,
  styleUrls: ['./movies.scss']
})
export class MoviesComponent {
  movies?: Movie[];  
  constructor(protected route: ActivatedRoute) {
    this.route.data.subscribe( ( data ) => this.dataChanged( data ) );
  }
  dataChanged(data: any) {
    console.log("Data Changed ", data);
    this.movies = data.movies;
  }
}
