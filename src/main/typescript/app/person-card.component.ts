import { Component, Input } from '@angular/core';

import { Person } from './service/movies.service';

@Component({
  selector: 'person-card',
  template: `
    <mat-card>
      <mat-card-header>
        <img mat-card-avatar [src]="person.img"/>
        <mat-card-title>{{person.name}} [{{person.id}}]</mat-card-title>  
      </mat-card-header>
      <mat-list>
        <mat-list-item *ngFor="let movie of person.movies">
          <img mat-list-icon [src]="movie.img"/>
          <div mat-line>{{movie.name}} [{{movie.id}}]</div>
        </mat-list-item>
      </mat-list>
    </mat-card>
  
`,
  styleUrls: ['./movies.scss']
})
export class PersonCardComponent {
  @Input() person!: Person;
}
