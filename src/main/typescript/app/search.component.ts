import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { MoviesService } from './service/movies.service';

@Component({
  selector: 'movie-search',
  template: `
    <mat-form-field>
      <mat-icon>search</mat-icon>
      <input matInput #inputElement [formControl]="textInputFormControl" (blur)='checkChanged($event)' (keydown.enter)='checkChanged($event)'>
      <mat-hint>{{hint}}</mat-hint>
    </mat-form-field>

`,
  styleUrls: ['./movies.scss']
})
export class SearchComponent {
  textInputFormControl = new FormControl();
  searchText?: string;
  searchDelay: any;

  constructor(protected route: ActivatedRoute, protected router: Router, protected moviesService: MoviesService) {
      this.route.queryParams.subscribe((queryParams) => this.queryParamsChanged(queryParams));

  }

  queryParamsChanged(queryParams: Params) {
      console.log("Params changed ", queryParams);
      if (queryParams['query']) {
        this.textInputFormControl.setValue(queryParams['query']);
      }
  }

  get hint(): string {
    return '';
  }
  
  get label(): string {
    return 'Search ';
  }
  
  checkChanged(event: Event) {
    console.log("Text lost focus checkChanged", event, this.textInputFormControl.value, this.textInputFormControl.value, this.router.routerState);
    this.router.navigate(this.route.snapshot.url, {queryParams: {query: this.textInputFormControl.value}});
  }
}
