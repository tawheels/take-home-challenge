package net.ggl.thc.crud;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.ggl.thc.pojo.Movie;
import net.ggl.thc.pojo.Person;

public class MongoMoviesLoader extends MoviesLoader {

  @Inject
  static MovieLoaderProperties movieLoaderProperties;
  MongoDatabase database;
  MongoCollection<Person> peopleCollection;
  MongoCollection<Movie> moviesCollection;
  List<Movie> movies;
  List<Person> people;
  
  public MongoMoviesLoader() throws Exception {
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    // log.warn("Database URI {}" + movieLoaderProperties.databaseURI);
    MongoClient mongoClient = MongoClients.create("mongodb+srv://gglthc:Mb^4f9PGPj@cluster0.s4e86.mongodb.net/gglthc");
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    database = mongoClient.getDatabase("gglthc").withCodecRegistry(pojoCodecRegistry);

    peopleCollection = database.getCollection("people", Person.class);
    moviesCollection = database.getCollection("movies", Movie.class);
  }
  
  

  @Override
  protected void reindex() throws Exception {
    movies = null;
    people = null;
    super.reindex();
  }



  @Override
  public Movie getMovie(String id) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Movie> getMovies() throws Exception {
    if (movies == null) {
      movies = new ArrayList<Movie>();
      moviesCollection.find().into(movies);
    }
    return movies;
  }

  @Override
  public Movie updateMovie(Movie movie) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Movie> addMovie(Movie movie) throws Exception {
    moviesCollection.insertOne(movie);
    reindex();
    return getMovies();
  }

  @Override
  public List<Movie> deleteMovie(Movie movie) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Person getPerson(String id) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Person> getPeople() throws Exception {
    if (people == null) {
      people = new ArrayList<Person>();
      peopleCollection.find().into(people);
    }
    return people;
  }

  @Override
  public Person updatePerson(Person person) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Person> addPerson(Person person) throws Exception {
    peopleCollection.insertOne(person);
    reindex();
    return getPeople();
  }

  @Override
  public List<Person> deletePerson(Person person) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

// This is here to bootstrap the database from the movies.json 
//  public static void main(String args[]) throws Exception {
//    MongoMoviesLoader mongoMoviesLoader = new MongoMoviesLoader();
//    FileMoviesLoader fileMoviesLoader = new FileMoviesLoader();
//    for (Movie movie : fileMoviesLoader.getMovies()) {
//      mongoMoviesLoader.addMovie(movie);
//    }
//    for (Person person: fileMoviesLoader.getPeople()) {
//      mongoMoviesLoader.addPerson(person);
//    }
//
//  }
}
