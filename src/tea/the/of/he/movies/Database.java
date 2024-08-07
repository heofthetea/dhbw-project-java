package tea.the.of.he.movies;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import tea.the.of.he.movies.entities.Actor;
import tea.the.of.he.movies.entities.Director;
import tea.the.of.he.movies.entities.Movie;
import tea.the.of.he.movies.entities.ActorMovie;
import tea.the.of.he.movies.entities.DirectorMovie;

/*
 * SINGLETON Class which serves as a central node to manage every aspect of the database.
 * Here, Lists of all entities and relations are stored, and methods for each necessary operation are provided,
 * to keep it as similar to a real database as possible, which you should have used in the first place.
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

    public void destroy() {
        instance = null;
    }


    //-------------------------------------------------------------------------------------------------
    // Searching Operations


    /**
     * Searches for all movies based on a title String
     * The case of the string is ignored, meaning 'test' will match a query string 'Test'.
     *
     * @param title The title of the movie to search for
     * @return List of all matches
     */
    public List<Movie> getMoviesByTitle(String title) {
        return movies.values().stream().filter(m -> m.getTitle().toLowerCase().contains(title.toLowerCase())).toList();
    }

    /**
     * Searches for all actors based on a name String
     * The case of the string is ignored, meaning 'test' will match a query string 'Test'.
     *
     * @param name The name of the actor to search for
     * @return List of all matches
     */
    public List<Actor> getActorsByName(String name) {
        return actors.values().stream().filter(a -> a.getName().toLowerCase().contains(name.toLowerCase())).toList();
    }

    public List<Movie> getMoviesByActor(Actor actor) {
        return actsInMovies.values().stream().filter(am -> am.actor().equals(actor)).map(ActorMovie::movie).toList();
    }

    public List<Actor> getActorsByMovie(Movie movie) {
        return actsInMovies.values().stream().filter(am -> am.movie().equals(movie)).map(ActorMovie::actor).toList();
    }


    // -------------------------------------------------------------------------------------------------
    // Inserting/Adding Operations #OOPhell
    // Encapsulation sadly brings along with it a lot of boilerplate and redundant
    // code. But hey, it's easy to use :)

    /**
     * Adds an Actor to the database.
     * The id of each actor object is extracted from the passed object, and placed
     * as the key for the respective hashmap.
     *
     * @param actor
     */
    public void add(Actor actor) {
        if (actors.containsKey(actor.getId())) {
            System.out.println("Duplicate Actor: " + actor.getId());
            return;

        }
        actors.put(actor.getId(), actor);
    }

    /**
     * Adds a Movie to the database.
     * The id of each Movie object is extracted from the passed object, and placed
     * as the key for the respective hashmap.
     * (Btw Johannes I'm sorry, but if I want to have nice method documentation
     * preview in my IDE, I sadly need to duplicate the comments just as I do the
     * methods for overloading.)
     *
     * @param movie
     */
    public void add(Movie movie) {
        if (movies.containsKey(movie.getId())) {
            // System.out.println("Duplicate Movie: " + movie.getId());
            return;
        }
        movies.put(movie.getId(), movie);
    }

    /**
     * Adds a Director to the database.
     * The id of each Director object is extracted from the passed object, and
     * placed as the key for the respective hashmap.
     *
     * @param director
     */
    public void add(Director director) {
        if (movies.containsKey(director.getId())) {
            System.out.println("Duplicate Director: " + director.getId());
            return;
        }
        directors.put(director.getId(), director);
    }

    /**
     * @param am ActorMovie relation
     */
    public void add(ActorMovie am) {
        if (actsInMovies.containsKey(am.hashCode())) {
            System.out.println(am.actor().getName() + " is already in " + am.movie().getTitle());
            return;
        }


        if (!(actors.containsKey(am.actor().getId()) && movies.containsKey(am.movie().getId()))) {
            System.out.println(am.actor().getName() + " or " + am.movie().getTitle() + " is not in the database.");
            return;
        }
        this.actsInMovies.put(am.hashCode(), am);
    }

    /**
     * @param dm DirectorMovie relation
     */
    public void add(DirectorMovie dm) {
        if (directsMovies.containsKey(dm.hashCode())) {
            System.out.println(dm.director().getName() + " is already directing " + dm.movie().getTitle());
            return;
        }

        if (!(directors.containsKey(dm.director().getId()) && movies.containsKey(dm.movie().getId()))) {
            System.out.println(dm.director().getName() + " or " + dm.movie().getTitle() + " is not in the database.");
            return;
        }
        this.directsMovies.put(dm.hashCode(), dm);
    }


    // -------------------------------------------------------------------------------------------------
    // Getters and Setters
    // These really only serve the purpose of easy testing. I might remove them later to have proper OOP encapsulation.

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
