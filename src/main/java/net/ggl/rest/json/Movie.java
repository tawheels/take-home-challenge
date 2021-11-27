package net.ggl.rest.json;

import java.util.ArrayList;

public class Movie {
  String id;
  String img;
  String name;
  String release;
  
  ArrayList<String> starring;
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

  public ArrayList<String> getStarring() {
    return starring;
  }
  public void setStarring(ArrayList<String> starring) {
    this.starring = starring;
  }
  public String getRelease() {
    return release;
  }
  public void setRelease(String release) {
    this.release = release;
  }
  
}
