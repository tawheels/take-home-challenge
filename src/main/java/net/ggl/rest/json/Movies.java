package net.ggl.rest.json;

import java.util.List;

public class Movies {
  List<Movie> movies;
  List<Person> people;
  public List<Movie> getMovies() {
    return movies;
  }
  public void setMovies(List<Movie> movies) {
    this.movies = movies;
  }
  public List<Person> getPeople() {
    return people;
  }
  public void setPeople(List<Person> people) {
    this.people = people;
  }
  
  
}
