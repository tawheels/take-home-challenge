package net.ggl.rest.json;

public class Base {
  String id;
  String img;
  String name;

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

  public String getBaseClass() {
    return getClass().getSimpleName();
  }
}
