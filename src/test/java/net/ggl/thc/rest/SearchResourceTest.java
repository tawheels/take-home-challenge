package net.ggl.thc.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import com.mongodb.BasicDBObject;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SearchResourceTest {

  @Test
  public void testSearchMovie() {
    BasicDBObject bodyJSON = new BasicDBObject();
    bodyJSON.put("query", "Ir");
    bodyJSON.put("baseClass", "Movie");
    
    given()
          .contentType("application/json")
          .body(bodyJSON.toJson())
          .when().post("/service/search")
          .then()
          .statusCode(200)
          .body(allOf(JSONMatchers.hasJSONArrayOfLength(2), containsString("Iron Man")));
  }

  @Test
  public void testSearchMovieId() {
    BasicDBObject bodyJSON = new BasicDBObject();
    bodyJSON.put("query", "imii");
    bodyJSON.put("baseClass", "Movie");
    
    given()
          .contentType("application/json")
          .body(bodyJSON.toJson())
          .when().post("/service/search")
          .then()
          .statusCode(200)
          .body(allOf(JSONMatchers.hasJSONArrayOfLength(1), containsString("Iron Man II")));
  }

  @Test
  public void testSearchPeople() {
    BasicDBObject bodyJSON = new BasicDBObject();
    bodyJSON.put("query", "mr");
    bodyJSON.put("baseClass", "Person");
    
    given()
          .contentType("application/json")
          .body(bodyJSON.toJson())
          .when().post("/service/search")
          .then()
          .statusCode(200)
          .body(allOf(JSONMatchers.hasJSONArrayOfLength(2), containsString("Mark Ruffalo")));
  }

}