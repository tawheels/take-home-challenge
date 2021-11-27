package net.ggl.rest.json;

import java.io.InputStream;
import java.util.List;

import javax.enterprise.event.Observes;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.ext.web.Router;

public class MoviesLoader {
  Movies movies;

  public void init(@Observes Router router) {
    router.errorHandler(404, routingContext -> {
        routingContext.response().setStatusCode(302).putHeader("Location", "/index.html").end();
    });
  }
  
  public static MoviesLoader movieLoader;
  public static MoviesLoader instance() {
    if (movieLoader == null) {
      movieLoader = new MoviesLoader();
    }
    return movieLoader;
  }
  
  public MoviesLoader() {
    if (movies == null) {
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        InputStream fileStream = getClass().getResourceAsStream("/movies.json");
        movies = objectMapper.readValue(fileStream, Movies.class);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public List<Movie> getMovies() {
    return movies.getMovies();
  }
  
  public List<Person> getPeople() {
    return movies.getPeople();
  }
}
