package net.ggl.thc.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class MovieResourceTest {

    @Test
    public void testMovieEndpoint() {
        given()
          .when().get("/service/movies")
          .then()
             .statusCode(200)
             .body(containsString("Iron Man"));
    }

}