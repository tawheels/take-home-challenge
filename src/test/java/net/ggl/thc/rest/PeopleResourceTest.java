package net.ggl.thc.rest;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PeopleResourceTest {

  @Test
  public void testPeopleEndpoint() {
    given()
          .when().get("/service/people")
          .then()
          .statusCode(200)
          .body(JSONMatchers.hasJSONArrayOfLength(37));
  }

}