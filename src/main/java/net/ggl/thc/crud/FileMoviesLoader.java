package net.ggl.thc.crud;

import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.ggl.thc.pojo.Movie;
import net.ggl.thc.pojo.Movies;
import net.ggl.thc.pojo.Person;

public class FileMoviesLoader extends MoviesLoader {
  Movies movies;

  public FileMoviesLoader() {
    if (movies == null) {
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        InputStream fileStream = getClass().getResourceAsStream("/movies.json");
        movies = objectMapper.readValue(fileStream, Movies.class);
        fileStream.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public Movie getMovie(String id) {
    return allMovie.get(id);
  }

  @Override
  public List<Movie> getMovies() {
    return movies.getMovies();
  }

  @Override
  public Person getPerson(String id) {
    return allPerson.get(id);
  }

  @Override
  public List<Person> getPeople() {
    return movies.getPeople();
  }

  @Override
  public Movie updateMovie(Movie movie) throws Exception {
    Movie oldMovie = allMovie.get(movie.getId());
    if (oldMovie != null) {
      int index = movies.getMovies().indexOf(oldMovie);
      if (index >= 0) {
        movies.getMovies().set(index, movie);
      }
    }
    return movie;
  }

  @Override
  public List<Movie> addMovie(Movie movie) throws Exception {
    movies.getMovies().add(movie);
    return movies.getMovies();
  }

  @Override
  public List<Movie> deleteMovie(Movie movie) throws Exception {
    movies.getMovies().removeIf(existingMovie -> existingMovie.getName().contentEquals(movie.getName()));
    return movies.getMovies();
  }

  @Override
  public Person updatePerson(Person person) throws Exception {
    Person oldPerson = allPerson.get(person.getId());
    if (oldPerson != null) {
      int index = movies.getPeople().indexOf(oldPerson);
      if (index >= 0) {
        movies.getPeople().set(index, person);
      }
    }
    return person;
  }

  @Override
  public List<Person> addPerson(Person person) throws Exception {
    movies.getPeople().add(person);
    return movies.getPeople();
  }

  @Override
  public List<Person> deletePerson(Person person) throws Exception {
    movies.getPeople().removeIf(existingPerson -> existingPerson.getName().contentEquals(person.getName()));
    return movies.getPeople();
  }

}
