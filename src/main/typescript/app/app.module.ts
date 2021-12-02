import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';

import { Location, LocationStrategy, HashLocationStrategy } from '@angular/common';

import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar'; 
import { TextFieldModule } from '@angular/cdk/text-field';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MovieCardComponent } from './movie-card.component';
import { MoviesComponent } from './movies.component';
import { PersonCardComponent } from './person-card.component';
import { PeopleComponent } from './people.component';
import { SearchComponent } from './search.component';


import { MoviesErrorHandler } from './service/message.service';

@NgModule({
  declarations: [
    AppComponent,
    MovieCardComponent,
    MoviesComponent,
    PersonCardComponent,
    PeopleComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatTabsModule,
    MatToolbarModule,
    ReactiveFormsModule,
    TextFieldModule
  ],
  providers: [
    Location, { provide: LocationStrategy, useClass: HashLocationStrategy },
    {provide: ErrorHandler, useClass: MoviesErrorHandler}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
