package net.ggl.thc.crud;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("net.ggl.thc.crud")
public class MovieLoaderProperties {
  public String className = "net.ggl.thc.crud.FileMoviesLoader";
  public String databaseURI;
  
}
