package net.ggl.thc.rest;

import org.hamcrest.Matcher;

public class JSONMatchers {
  public static Matcher<String> hasJSONArrayOfLength(int length) {
    return new HasJSONArrayOfLengthMatcher(length);
  }
}
