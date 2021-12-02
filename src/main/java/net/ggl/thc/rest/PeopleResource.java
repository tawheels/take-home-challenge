package net.ggl.thc.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import net.ggl.thc.crud.MoviesLoader;
import net.ggl.thc.pojo.Person;

@Path("/service/people")
public class PeopleResource {

  @GET
  public List<Person> list() throws Exception {
    return MoviesLoader.instance().getPeople();
  }

  @GET
  @Path("{id}")
  public Person get(@PathParam("id") String id) throws Exception {
    return MoviesLoader.instance().getPerson(id);
  }

  @POST
  public List<Person> add(Person person) throws Exception {
    return MoviesLoader.instance().addPerson(person);
  }

  @DELETE
  public List<Person> delete(Person person) throws Exception {
    return MoviesLoader.instance().deletePerson(person);
  }
}