package net.ggl.rest.json;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.logging.Logger;

@Path("/service/search")
public class SearchResource {
  private static final Logger log = Logger.getLogger(SearchResource.class);

  @POST
//  @Produces(MediaType.APPLICATION_JSON)
//  @Consumes(MediaType.APPLICATION_JSON)
  public List<Base> list(Search query) throws Exception {
    log.warn("Search for query " + query.getQuery() + " baseClass: " + query.getBaseClass());
    return MoviesLoader.instance().search(query.getQuery());
  }

}
