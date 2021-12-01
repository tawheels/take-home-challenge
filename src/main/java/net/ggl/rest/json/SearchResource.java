package net.ggl.rest.json;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.logging.Logger;

@Path("/service/search")
public class SearchResource {
  private static final Logger log = Logger.getLogger(SearchResource.class);

  @POST
  public List<Base> list(Search search) throws Exception {
    log.warn("Search for query " + search.getQuery() + " baseClass: " + search.getBaseClass());
    return MoviesLoader.instance().search(search);
  }

}
