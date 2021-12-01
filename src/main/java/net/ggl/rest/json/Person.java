package net.ggl.rest.json;

import java.util.ArrayList;

public class Person extends Base {
  ArrayList<String> movieIds;
  ArrayList<Base> movies;

  public ArrayList<String> getMovieIds() {
    return movieIds;
  }

  public void setMovieIds(ArrayList<String> movieIds) {
    this.movieIds = movieIds;
  }

  public ArrayList<Base> getMovies() {
    return movies;
  }

  public void setMovies(ArrayList<Base> movies) {
    this.movies = movies;
  }

}
