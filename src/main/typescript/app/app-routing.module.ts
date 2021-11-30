import { NgModule, Injectable } from '@angular/core';
import { RouterModule, Routes, Resolve } from '@angular/router';

import { Observable } from 'rxjs';

import { MoviesService, Movie, Person } from './service/movies.service';
import { MoviesComponent } from './movies.component';
import { PeopleComponent } from './people.component';

@Injectable()
export class MoviesList implements Resolve<any> {
  constructor(protected moviesService: MoviesService) {
  }

  resolve(): Observable<any> {
    return this.moviesService.serviceGet<Movie[]>("/service/movies", {});
  }
}

@Injectable()
export class PeopleList implements Resolve<any> {
  constructor(protected moviesService: MoviesService) {
  }

  resolve(): Observable<any> {
    return this.moviesService.serviceGet<Person[]>("/service/people", {});
  }
}

const routes: Routes = [
  { path: '', 
    redirectTo: '/movies', 
    pathMatch: 'full' 
  },
  {
      path: 'movies',
      // path: '/model/dashboard',
      component: MoviesComponent,
      resolve: {
        movies: MoviesList
      }
  },
  {
      path: 'people',
      // path: '/model/dashboard',
      component: PeopleComponent,
      resolve: {
        people: PeopleList
      }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [
     MoviesList,
     PeopleList
  ]
})
export class AppRoutingModule { }
