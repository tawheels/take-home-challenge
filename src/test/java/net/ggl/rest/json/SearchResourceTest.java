package net.ggl.rest.json;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SearchResourceTest {

  @Test
  public void testSearchEndpoint() {
    String body = "{\"query\": \"Ir\", \"baseClass\": \"Movie\"}";
    // postParams.put("baseClass", "");

    given()
          .contentType("application/json")
          .body(body)
          .when().post("/service/search")
          .then()
          .statusCode(200)
          .body(containsString("Iron Man"));
  }

}