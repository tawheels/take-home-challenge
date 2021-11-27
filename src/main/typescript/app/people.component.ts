import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Person } from './service/movies.service';

@Component({
  selector: 'people',
  template: `
    <div class="people">
      <ng-container *ngIf="people">
        <person-card *ngFor="let person of people" [person]="person"></person-card>
      </ng-container>
    </div>
  
`,
  styleUrls: ['./movies.scss']
})
export class PeopleComponent {
  people?: Person[];  
  constructor(protected route: ActivatedRoute) {
    this.route.data.subscribe( ( data ) => this.dataChanged( data ) );
  }
  dataChanged(data: any) {
    console.log("Data Changed ", data);
    this.people = data.people;
  }
}
