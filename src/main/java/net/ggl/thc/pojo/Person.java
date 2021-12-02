package net.ggl.thc.pojo;

import java.util.ArrayList;

import org.bson.codecs.pojo.annotations.BsonIgnore;

public class Person extends Base {
  ArrayList<String> movieIds;
  ArrayList<Base> movies;

  public ArrayList<String> getMovieIds() {
    return movieIds;
  }

  public void setMovieIds(ArrayList<String> movieIds) {
    this.movieIds = movieIds;
  }

  @BsonIgnore
  public ArrayList<Base> getMovies() {
    return movies;
  }

  @BsonIgnore
  public void setMovies(ArrayList<Base> movies) {
    this.movies = movies;
  }

}
