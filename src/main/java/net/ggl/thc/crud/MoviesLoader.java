package net.ggl.thc.crud;

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
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import net.ggl.thc.pojo.Base;
import net.ggl.thc.pojo.Movie;
import net.ggl.thc.pojo.Person;
import net.ggl.thc.pojo.Search;

public abstract class MoviesLoader {
  public static String idKey = "id";
  public static String baseClassKey = "baseClass";
  public static String nameKey = "name";
  protected static final Logger log = Logger.getLogger(MoviesLoader.class);
  HashMap<String, Base> allBase;
  HashMap<String, Person> allPerson;
  HashMap<String, Movie> allMovie;


  public static MoviesLoader movieLoader;
  IndexSearcher searcher;
  Analyzer analyzer;
  String indexPath = "/tmp/lucene";

  public static MoviesLoader instance() {
    if (movieLoader == null) {
      String loaderClass = ConfigProvider.getConfig().getValue("net.ggl.thc.crud.className", String.class).trim(); 
      try {
        movieLoader = (MoviesLoader) MoviesLoader.class.getClassLoader().loadClass(loaderClass).getDeclaredConstructor().newInstance();
        movieLoader.reindex();
      }
      catch (Exception e) {
        log.error("Could not create instance of " + loaderClass, e);
        // movieLoader = new FileMoviesLoader();
      }
    }
    return movieLoader;
  }

  void indexBase(Base base, IndexWriter writer) throws Exception {
    Document doc = new Document();
    doc.add(new TextField(idKey, base.getId(), Field.Store.YES));
    doc.add(new StringField(baseClassKey, base.getBaseClass(), Field.Store.YES));
    doc.add(new TextField(nameKey, base.getName(), Field.Store.YES));
    writer.addDocument(doc);

  }

  protected void reindex() throws Exception {
    allBase = new HashMap<String, Base>();
    allMovie = new HashMap<String, Movie>();
    allPerson = new HashMap<String, Person>();

    Analyzer analyzer = new StandardAnalyzer();
    IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
    iwc.setOpenMode(OpenMode.CREATE);
    IndexWriter writer = new IndexWriter(FSDirectory.open(Paths.get(indexPath)), iwc);
    for (Movie movie : getMovies()) {
      indexBase(movie, writer);
      allMovie.put(movie.getId(), movie);
      allBase.put(movie.getId(), movie);
    }
    for (Person person : getPeople()) {
      indexBase(person, writer);
      allPerson.put(person.getId(), person);
      allBase.put(person.getId(), person);
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
    for (Movie movie : getMovies()) {
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
      parser = new SimpleQueryParser(analyzer, idKey);
      query = parser.parse(searchQuery);
      results = searcher.search(query, 100);
      log.warn("Search results " + results.totalHits.value);
      for (ScoreDoc hit : results.scoreDocs) {
        Document doc = searcher.doc(hit.doc);
        Base thisBase = allBase.get((String) doc.get(idKey));
        if (thisBase != null && !searchResults.contains(thisBase) && search.getBaseClass() != null && thisBase.getBaseClass() != null &&
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

  public abstract Movie getMovie(String id) throws Exception;

  public abstract List<Movie> getMovies() throws Exception;

  public abstract Movie updateMovie(Movie movie) throws Exception;

  public abstract List<Movie> addMovie(Movie movie) throws Exception;
  
  public abstract List<Movie> deleteMovie(Movie movie) throws Exception;
  
  public abstract Person getPerson(String id) throws Exception;

  public abstract List<Person> getPeople() throws Exception;

  public abstract Person updatePerson(Person person) throws Exception;

  public abstract List<Person> addPerson(Person person) throws Exception;
  
  public abstract List<Person> deletePerson(Person person) throws Exception;

}
