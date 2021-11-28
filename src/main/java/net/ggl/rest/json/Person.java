package net.ggl.rest.json;

import java.util.ArrayList;

public class Person extends Base {

  ArrayList<String> movies;

  public ArrayList<String> getMovies() {
    return movies;
  }

  public void setMovies(ArrayList<String> movies) {
    this.movies = movies;
  }

}
