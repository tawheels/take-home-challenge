package net.ggl.rest.json;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/service/movies")
public class MovieResource {

  @GET
  public List<Movie> list() {
    return MoviesLoader.instance().getMovies();
  }

  @GET
  @Path("{id}")
  public Movie get(@PathParam("id") String id){
    return MoviesLoader.instance().getMovie(id);
  }
  
  @POST
  public List<Movie> add(Movie movie) {

    list().add(movie);
    return list();
  }

  @DELETE
  public List<Movie> delete(Movie movie) {
    list().removeIf(existingMovie -> existingMovie.name.contentEquals(movie.name));
    return list();
  }
}