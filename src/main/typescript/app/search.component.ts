import { Component } from '@angular/core';

import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { Observable, Subscription } from 'rxjs';

import { MoviesService, Base, Movie, Person } from './service/movies.service';

@Component({
  selector: 'movie-search',
  template: `
      <mat-form-field>
        <input matInput #inputElement 
          [formControl]="textInputFormControl" 
          [matAutocomplete]="autoComplete" 
          aria-label='text input'>
        <mat-autocomplete panelWidth="300px" #autoComplete="matAutocomplete" (optionSelected)="autocompleteSelected($event)">
          <mat-option class="search-result" *ngFor="let result of filteredBaseChoices | async" [value]="result">
            <div class="model-tab-template">
              <div class="name">
                {{result.name}}
              </div>
            </div>
          </mat-option>
        </mat-autocomplete>
      </mat-form-field>

`,
  styleUrls: ['./movies.scss']
})
export class SearchComponent {
  textInputFormControl = new FormControl();
  textChangeObserverSubscription: Subscription;
  filteredBaseChoices?: Observable<Base[]>;
  showSearch?: boolean;
  searchText?: string;
  searchDelay: any;

  constructor(protected moviesService: MoviesService) {
    this.textChangeObserverSubscription = this.textInputFormControl.valueChanges.subscribe((searchText) => {
      // console.log("Values Changed", searchText);
      // this.showAllChoices = false;
      // this.autoSearchFormControl.setValue(searchText);
      if (!searchText || !(searchText instanceof Object)) {
        this.searchText = searchText;
        clearTimeout(this.searchDelay);
        this.searchDelay = setTimeout(() => {
          this.filteredBaseChoices = this.filterChoicesObservable(searchText);
        }, 300);
      }
    });
  }

  filterChoicesObservable(searchText: string): Observable<Base[]> {
    let _filterChoicesObservable = new Observable<Base[]>((observer) => {

      this.moviesService.servicePost<Base[]>("/service/search", { query: searchText, baseClass: 'All' }).subscribe((response: any) => {
        observer.next(response['choices']);
      });
    });
    return _filterChoicesObservable;
  }
  
  autocompleteSelected(event: MatAutocompleteSelectedEvent): void {
    console.log("autocompleteSelected ", event.option, event.option.value, this.searchText);
    this.textInputFormControl.setValue(this.searchText);
//    let searchResult: Base = event.option.value;
//    if (searchResult.link) {
//      this.moviesService.router.navigate(searchResult.link.routerLink, { queryParams: searchResult.link.queryParams });
//    }
  }

}
