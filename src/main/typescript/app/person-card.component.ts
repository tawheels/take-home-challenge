import { Component, Input } from '@angular/core';

import { Person } from './service/movies.service';

@Component({
  selector: 'person-card',
  template: `
    <mat-card>
      <mat-card-header>
        <img mat-card-avatar [src]="person.img"/>
        <mat-card-title>{{person.name}}</mat-card-title>  
      </mat-card-header>
    </mat-card>
  
`,
  styleUrls: ['./movies.scss']
})
export class PersonCardComponent {
  @Input() person!: Person;
}
