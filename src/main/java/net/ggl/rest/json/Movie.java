package net.ggl.rest.json;

import java.util.ArrayList;

public class Movie extends Base {
  String release;

  ArrayList<String> starring;

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
