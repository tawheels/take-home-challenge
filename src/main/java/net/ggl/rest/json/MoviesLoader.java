package net.ggl.rest.json;

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
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(FSDirectory.open(Paths.get(indexPath)), iwc);
        for (Movie movie : movies.getMovies()) {
          indexBase(movie, writer);
        }
        for (Person person : movies.getPeople()) {
          indexBase(person, writer);
        }
        writer.flush();
        writer.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public List<Base> search(String queryString) throws Exception {
    if (searcher == null) {
      IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
      searcher = new IndexSearcher(reader);
      analyzer = new StandardAnalyzer();
    }
    ArrayList<Base> searchResults = new ArrayList<Base>();  
    if (queryString != null && queryString.length() > 0) {
      QueryParser parser = new QueryParser(nameKey, analyzer);
      Query query = parser.parse(queryString);
      TopDocs results = searcher.search(query, 100);
      log.warn("Search results " + results.totalHits.value);
      for (ScoreDoc hit : results.scoreDocs) {
        Document doc = searcher.doc(hit.doc);
        Base thisBase = allBase.get((String) doc.get(idKey));
        if (thisBase != null) {
          searchResults.add(thisBase);
        }
        else {
          log.error("Could not find Base for id " + doc.get(idKey));
        }
      }
    }
    return searchResults;
  }

  public List<Movie> getMovies() {
    return movies.getMovies();
  }

  public List<Person> getPeople() {
    return movies.getPeople();
  }
}
