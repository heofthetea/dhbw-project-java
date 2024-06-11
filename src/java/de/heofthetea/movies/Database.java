package de.heofthetea.movies;

import java.util.Map;
import java.util.HashMap;
import de.heofthetea.movies.entities.Actor;
import de.heofthetea.movies.entities.Director;
import de.heofthetea.movies.entities.Movie;
import de.heofthetea.movies.entities.ActorMovie;
import de.heofthetea.movies.entities.DirectorMovie;

/*
 * SINGLETON Class which serves as a central node to manage every aspect of the database.
 * Here, Lists of all entities and relations are stored, and methods for each necessary operation are provided,
 * to keep it as similar to a real databese as possible, which you should have used in the first place.
 */
public final class Database {
    private static Database instance;

    // I was considering making these public to avoid SPI boilerplate, but if we're
    // going for hardcore OOP, we should do hardcore OOP.
    private Map<Integer, Actor> actors;
    private Map<Integer, Movie> movies;
    private Map<Integer, Director> directors;
    private Map<Integer, ActorMovie> actsInMovies;
    private Map<Integer, DirectorMovie> directsMovies;

    private Database() {
        this.actors = new HashMap<>();
        this.movies = new HashMap<>();
        this.directors = new HashMap<>();
        this.actsInMovies = new HashMap<>();
        this.directsMovies = new HashMap<>();
    }

    // Ensure Class is Singleton by always returning a stored instance of it
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    

    // -------------------------------------------------------------------------------------------------
    // Inserting/Adding Operations #OOPhell
    // Encapsulation sadly brings along with it a lot of boilerplate and redundant code. But hey, it's easy to use :)

    /**
     * Adds an Actor to the database.
     * The id of each actor object is extracted from the passed object, and placed
     * as the key for the respective hashmap.
     * 
     * @param actor
     */
    public void add(Actor actor) {
        this.actors.put(actor.getId(), actor);
    }

    /**
     * Adds a Movie to the database.
     * The id of each Movie object is extracted from the passed object, and placed
     * as the key for the respective hashmap.
     * (Btw Jonathan I'm sorry, but if I want to have nice method documentation
     * preview in my IDE, I sadly need to duplicate the comments just as I do the
     * methods for overloading.)
     * 
     * @param actorMovie
     */
    public void add(Movie movie) {
        this.movies.put(movie.getId(), movie);
    }

    /**
     * Adds a Director to the database.
     * The id of each Director object is extracted from the passed object, and
     * placed as the key for the respective hashmap.
     * 
     * @param director
     */
    public void add(Director director) {
        this.directors.put(director.getId(), director);
    }

    public void add(ActorMovie am) {
        this.actsInMovies.put(am.hashCode(), am);
    }
    public void add(DirectorMovie dm) {
        this.directsMovies.put(dm.hashCode(), dm);
    }

    // -------------------------------------------------------------------------------------------------
    // Getters and Setters

    public Map<Integer, Actor> getActors() {
        return this.actors;
    }

    public void setActors(Map<Integer, Actor> actors) {
        this.actors = actors;
    }

    public Map<Integer, Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(Map<Integer, Movie> movies) {
        this.movies = movies;
    }

    public Map<Integer, Director> getDirectors() {
        return this.directors;
    }

    public void setDirectors(Map<Integer, Director> directors) {
        this.directors = directors;
    }

    public Map<Integer, ActorMovie> getActsInMovies() {
        return this.actsInMovies;
    }

    public void setActsInMovies(Map<Integer, ActorMovie> actsInMovies) {
        this.actsInMovies = actsInMovies;
    }

    public Map<Integer, DirectorMovie> getDirectsMovies() {
        return this.directsMovies;
    }

    public void setDirectsMovies(Map<Integer, DirectorMovie> directsMovies) {
        this.directsMovies = directsMovies;
    }

}
