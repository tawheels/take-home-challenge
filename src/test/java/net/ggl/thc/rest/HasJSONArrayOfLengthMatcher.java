package net.ggl.thc.rest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class HasJSONArrayOfLengthMatcher extends BaseMatcher<String> {
  int elements;
  int actualElements;

  public HasJSONArrayOfLengthMatcher(int elements) {
    this.elements = elements;
  }

  @Override
  public boolean matches(Object actual) {

    try {
      BasicDBObject isList = BasicDBObject.parse("{\"list\": " + (String) actual + "}");
      BasicDBList list = (BasicDBList) isList.get("list");
      if (list != null) {
        actualElements = list.size();
        if (actualElements == elements) {
          return true;
        }
      }
    }
    catch (Exception e) {
      return false;
    }

    return false;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("has JSON array of ").appendValue(elements).appendText(" Actual Count is ").appendValue(actualElements);
  }

}
