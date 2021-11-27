package net.ggl.rest.json;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class MovieResourceTest {

    @Test
    public void testMovieEndpoint() {
        given()
          .when().get("/movies")
          .then()
             .statusCode(200)
             .body(contains("Iron Man"));
    }

}