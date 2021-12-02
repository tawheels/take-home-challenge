package net.ggl.thc.pojo;

import java.util.ArrayList;

import org.bson.codecs.pojo.annotations.BsonIgnore;

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

  @BsonIgnore
  public ArrayList<Base> getStarring() {
    return starring;
  }

  @BsonIgnore
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
