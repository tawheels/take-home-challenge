package net.ggl.thc.pojo;

import java.util.ArrayList;

public class Movie extends Base {
  String release;

  ArrayList<String> starringIds;
  ArrayList<Base> starring;

  public ArrayList<String> getStarringIds() {
    return starringIds;
  }

  public void setStarringIds(ArrayList<String> starringIds) {
    this.starringIds = starringIds;
  }

  public ArrayList<Base> getStarring() {
    return starring;
  }

  public void setStarring(ArrayList<Base> starring) {
    this.starring = starring;
  }

  public String getRelease() {
    return release;
  }

  public void setRelease(String release) {
    this.release = release;
  }

}
