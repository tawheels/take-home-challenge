package net.ggl.rest.json;

public class Base {
  String id;
  String img;
  String name;

  public Base() {
  }
  
  public Base(Base base) {
    this.id = base.id;
    this.name = base.name;
    this.img = base.img;
  }
  
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
