import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { Location, LocationStrategy, HashLocationStrategy } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MovieCardComponent } from './movie-card.component';
import { MoviesComponent } from './movies.component';
import { PersonCardComponent } from './person-card.component';
import { PeopleComponent } from './people.component';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar'; 

import { MoviesErrorHandler } from './service/message.service';

@NgModule({
  declarations: [
    AppComponent,
    MovieCardComponent,
    MoviesComponent,
    PersonCardComponent,
    PeopleComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    MatButtonModule,
    MatCardModule,
    MatToolbarModule
  ],
  providers: [
    Location, { provide: LocationStrategy, useClass: HashLocationStrategy },
    {provide: ErrorHandler, useClass: MoviesErrorHandler}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
