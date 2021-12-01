import { NgModule, Injectable } from '@angular/core';
import { RouterModule, Routes, Resolve, Router, ActivatedRouteSnapshot } from '@angular/router';

import { Observable } from 'rxjs';

import { MoviesService, Movie, Person } from './service/movies.service';
import { MoviesComponent } from './movies.component';
import { PeopleComponent } from './people.component';

@Injectable()
export class MoviesList implements Resolve<any> {
  constructor(protected router: Router, protected moviesService: MoviesService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<any> {
    console.log("Resolve people for ", route.queryParams);
    if (route.queryParams && route.queryParams['query']) {
      return this.moviesService.servicePost<Movie[]>("/service/search", {query: route.queryParams['query'], baseClass: 'Movie'});
    }
    return this.moviesService.serviceGet<Movie[]>("/service/movies", {});
  }
}

@Injectable()
export class PeopleList implements Resolve<any> {
  constructor(protected router: Router, protected moviesService: MoviesService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<any> {
    console.log("Resolve people for ", route.queryParams);
    if (route.queryParams && route.queryParams['query']) {
      return this.moviesService.servicePost<Person[]>("/service/search", {query: route.queryParams['query'], baseClass: 'Person'});
    }
    return this.moviesService.serviceGet<Person[]>("/service/people", {});
  }
}


const routes: Routes = [
  { path: '', 
    redirectTo: '/people', 
    pathMatch: 'full' 
  },
  {
      path: 'movies',
      // path: '/model/dashboard',
      component: MoviesComponent,
      runGuardsAndResolvers: 'always',
      resolve: {
        movies: MoviesList
      }
  },
  {
      path: 'people',
      // path: '/model/dashboard',
      component: PeopleComponent,
      runGuardsAndResolvers: 'always',
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
