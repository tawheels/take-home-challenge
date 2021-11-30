package net.ggl.rest.json;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/service/search")
public class SearchResource {

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public List<Base> list(Search query) {
    ArrayList<Base> results = new ArrayList<Base>();
    results.addAll(MoviesLoader.instance().getMovies());
    return results;
  }

}
