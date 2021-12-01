package net.ggl.rest.json;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/service/people")
public class PeopleResource {

  @GET
  public List<Person> list() {
    return MoviesLoader.instance().getPeople();
  }

  @GET
  @Path("{id}")
  public Person get(@PathParam("id") String id){
    return MoviesLoader.instance().getPerson(id);
  }

  @POST
  public List<Person> add(Person person) {
    list().add(person);
    return list();
  }

  @DELETE
  public List<Person> delete(Person person) {
    list().removeIf(existingPerson -> existingPerson.name.contentEquals(person.name));
    return list();
  }
}