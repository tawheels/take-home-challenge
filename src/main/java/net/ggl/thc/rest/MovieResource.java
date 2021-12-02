package net.ggl.thc.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import net.ggl.thc.crud.MoviesLoader;
import net.ggl.thc.pojo.Movie;

@Path("/service/movies")
public class MovieResource {

  @GET
  public List<Movie> list() throws Exception {
    return MoviesLoader.instance().getMovies();
  }

  @GET
  @Path("{id}")
  public Movie get(@PathParam("id") String id) throws Exception {
    return MoviesLoader.instance().getMovie(id);
  }
  
  @POST
  public List<Movie> add(Movie movie) throws Exception {
    return MoviesLoader.instance().addMovie(movie);
  }

  @DELETE
  public List<Movie> delete(Movie movie) throws Exception {
    return MoviesLoader.instance().deleteMovie(movie);
  }
}