package net.ggl.rest.json;

import java.util.List;

import org.jboss.logging.Logger;

public class Movies {
  private static final Logger log = Logger.getLogger(Movies.class);
  
  List<Movie> movies;
  List<Person> people;
  public List<Movie> getMovies() {
    log.warn("getMovie " + movies.size());
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
