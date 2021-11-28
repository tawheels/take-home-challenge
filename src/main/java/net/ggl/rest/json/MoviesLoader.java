package net.ggl.rest.json;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MoviesLoader {
  Movies movies;

  public static MoviesLoader movieLoader;

  public static MoviesLoader instance() {
    if (movieLoader == null) {
      movieLoader = new MoviesLoader();
    }
    return movieLoader;
  }

  void indexBase(Base base, IndexWriter writer) throws Exception {
    Document doc = new Document();
    doc.add(new StringField("id", base.getId(), Field.Store.YES));
    doc.add(new StringField("baseClass", base.getBaseClass(), Field.Store.YES));
    doc.add(new StringField("name", base.getName(), Field.Store.YES));
    writer.addDocument(doc);

  }

  public MoviesLoader() {
    if (movies == null) {
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        InputStream fileStream = getClass().getResourceAsStream("/movies.json");
        movies = objectMapper.readValue(fileStream, Movies.class);
        Directory dir = FSDirectory.open(Paths.get("/tmp/lucene"));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(dir, iwc);
        for (Movie movie : movies.getMovies()) {
          indexBase(movie, writer);
        }
        for (Person person : movies.getPeople()) {
          indexBase(person, writer);
        }
        writer.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public List<Movie> getMovies() {
    return movies.getMovies();
  }

  public List<Person> getPeople() {
    return movies.getPeople();
  }
}
