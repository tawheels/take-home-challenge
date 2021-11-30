package net.ggl.rest.json;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SearchResourceTest {

  @Test
  public void testSearchEndpoint() {
    HashMap<String, Object> postParams = new HashMap<String, Object>();
    postParams.put("query", "");
    // postParams.put("baseClass", "");

    given()
          .contentType("application/json")
          .when().post("/service/search")
          .then()
          .statusCode(200)
          .body(containsString("Iron Man"));
  }

}