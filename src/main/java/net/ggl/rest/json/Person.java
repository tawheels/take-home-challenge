package net.ggl.rest.json;

import java.util.ArrayList;

public class Person {
  String id;
  String img;
  String name;
  ArrayList<String> movies;
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getImg() {
    return img;
  }
  public void setImg(String img) {
    this.img = img;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public ArrayList<String> getMovies() {
    return movies;
  }
  public void setMovies(ArrayList<String> movies) {
    this.movies = movies;
  }
  
  
}
