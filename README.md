# Take Home Challenge 

I used this exercise as an excuse to try Quarkus [https://quarkus.io/](https://quarkus.io/). It claims to have a super small memory footprint and a very fast boot time.  In other words perfect for Microservices

This project includes an angular web interface. I know that was not a requirement but in building a working application I found a bunch of things that I would want to solve if I was going to use Quarkus in production and the browser debug tools are very helpful in confirming results.

There are two different persistence mechanisms.
1. Flat JSON file using Jackson POJO
2. MongoDB - This requires a local mongodb 4.4 database, or I can provide the url to a shared DB in the mongodb cloud. I would need the IP addresses of any machines from which you will want to access mongo.

I used Lucene for the search.  Currently every query searches both the ID and the Name fields for a match.  You cannot explicitly request an ID. With the current data set this is only a problem if you wanted to search for Mickey Rourke [MR] by id.


## prerequisites
Maven

Node

NPM

## Installing the application 
- If you are using the Flat JSON persistence and have the dependecies installed then everything should run straight out of the box.

- To run the mongo version
    
    - Load the database dump into you mongo instance or request the cloud.mongodb URI 
      Run mongorestore command in the this directory, if using a local database you should not need any parameters
    
    - modify the file src/main/resources/application.properties
      Place a \# in front of FileMovieLoader and remove the \# in front of MongoMoviesLoader

```
    #net.ggl.thc.crud.className=net.ggl.thc.crud.FileMoviesLoaderr
    net.ggl.thc.crud.className=net.ggl.thc.crud.MongoMoviesLoader 
    net.ggl.thc.crud.database-uri=mongodb://localhost/gglthc
```

## Running the application 

To run the application execute the shell script 

```t
./mvnw compile quarkus:dev
```
In a browser go to the url [http://localhost:8080](http://localhost:8080)

  - You can select People or Movies. 
  - A search will be performed by pressing [enter] or [tab]
      - There is no message if the results are empty
      - There is very minimal error checking
      
To run the tests execute the shell script 

```shell script
./mvnw verify
```


## Libraries/concepts included

##### RESTEasy JAX-R

##### Lucene

##### Jackson POJO 

##### MongoDB POJO 

##### Contexts and Dependency Injection

##### Factory Injection 

##### Angular Material

##### Custom Hamcrest Matcher

