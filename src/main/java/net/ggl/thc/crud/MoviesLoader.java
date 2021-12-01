package net.ggl.thc.crud;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.ggl.thc.pojo.Base;
import net.ggl.thc.pojo.Movie;
import net.ggl.thc.pojo.Movies;
import net.ggl.thc.pojo.Person;
import net.ggl.thc.pojo.Search;

public class MoviesLoader {
  public static String idKey = "id";
  public static String baseClassKey = "baseClass";
  public static String nameKey = "name";
  private static final Logger log = Logger.getLogger(MoviesLoader.class);

  Movies movies;

  public static MoviesLoader movieLoader;
  IndexSearcher searcher;
  Analyzer analyzer;
  String indexPath = "/tmp/lucene";
  HashMap<String, Base> allBase;
  HashMap<String, Person> allPerson;
  HashMap<String, Movie> allMovie;

  public static MoviesLoader instance() {
    if (movieLoader == null) {
      movieLoader = new MoviesLoader();
    }
    return movieLoader;
  }

  void indexBase(Base base, IndexWriter writer) throws Exception {
    Document doc = new Document();
    doc.add(new StringField(idKey, base.getId(), Field.Store.YES));
    doc.add(new StringField(baseClassKey, base.getBaseClass(), Field.Store.YES));
    doc.add(new TextField(nameKey, base.getName(), Field.Store.YES));
    allBase.put(base.getId(), base);
    writer.addDocument(doc);

  }

  public MoviesLoader() {
    if (movies == null) {
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        InputStream fileStream = getClass().getResourceAsStream("/movies.json");
        movies = objectMapper.readValue(fileStream, Movies.class);
        fileStream.close();
        allBase = new HashMap<String, Base>();
        allMovie = new HashMap<String, Movie>();
        allPerson = new HashMap<String, Person>();

        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(FSDirectory.open(Paths.get(indexPath)), iwc);
        for (Movie movie : movies.getMovies()) {
          indexBase(movie, writer);
          allMovie.put(movie.getId(), movie);
        }
        for (Person person : movies.getPeople()) {
          indexBase(person, writer);
          allPerson.put(person.getId(), person);
          if (person.getMovieIds() != null && person.getMovieIds().size() > 0) {
            ArrayList<Base> starredInMovies = new ArrayList<Base>();
            person.setMovies(starredInMovies);
            for (String movieId : person.getMovieIds()) {
              Movie starredInMovie = allMovie.get(movieId);
              if (starredInMovie != null) {
                starredInMovies.add(new Base(starredInMovie));
              }
            }
          }
        }
        for (Movie movie : movies.getMovies()) {
          ArrayList<Base> starring = new ArrayList<Base>();
          movie.setStarring(starring);
          for (String personId : movie.getStarringIds()) {
            Person person = allPerson.get(personId);
            if (person != null) {
              starring.add(new Base(person));
            }
          }
        }
        writer.flush();
        writer.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public List<Base> search(Search search) throws Exception {
    if (searcher == null) {
      IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
      searcher = new IndexSearcher(reader);
      analyzer = new StandardAnalyzer();
    }
    ArrayList<Base> searchResults = new ArrayList<Base>();
    String searchQuery = search.getQuery();
    
    if (searchQuery != null && searchQuery.length() > 0) {
      if (!searchQuery.endsWith("*")) {
        searchQuery = searchQuery + "*";
      }
      SimpleQueryParser parser = new SimpleQueryParser(analyzer, nameKey);
      Query query = parser.parse(searchQuery);
      TopDocs results = searcher.search(query, 100);
      log.warn("Search results " + results.totalHits.value);
      for (ScoreDoc hit : results.scoreDocs) {
        Document doc = searcher.doc(hit.doc);
        Base thisBase = allBase.get((String) doc.get(idKey));
        if (thisBase != null && search.getBaseClass() != null && thisBase.getBaseClass() != null &&
              (search.getBaseClass().equalsIgnoreCase("all") || search.getBaseClass().equalsIgnoreCase(thisBase.getBaseClass()))) {
          searchResults.add(thisBase);
        }
        else {
          log.error("Could not find Base for id " + doc.get(idKey));
        }
      }
    }
    return searchResults;
  }

  public Movie getMovie(String id) {
    return allMovie.get(id);
  }

  public List<Movie> getMovies() {
    return movies.getMovies();
  }

  public Person getPerson(String id) {
    return allPerson.get(id);
  }

  public List<Person> getPeople() {
    return movies.getPeople();
  }
}
