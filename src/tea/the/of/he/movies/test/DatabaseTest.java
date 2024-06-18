package tea.the.of.he.movies.test;

import org.junit.Test;
import static org.junit.Assert.*;

import tea.the.of.he.movies.entities.*;
import tea.the.of.he.movies.Database;

public class DatabaseTest {
    @Test

    /*
     * GIVEN a database with actors in it
     * 
     * WHEN a new actor is added to the database
     * THEN the actor is added to the database;
     * 
     * WHEN a new actor with an already existing ID is added to the database
     * THEN the actor is not added to the database.
     */
    public void addActorTest() {
        Database db = Database.getInstance();

        db.add(new Actor(0, "Actor 1"));
        db.add(new Actor(1, "Actor 2"));
        db.add(new Actor(0, "Actor 4"));

        assertEquals(2, db.getActors().size());
        assertNotEquals(null, db.getActors().get(0));

        db.destroy();
    }

    /*
     * GIVEN a database with movies in it
     * 
     * WHEN a new movie is added to the database
     * THEN the movie is added to the database;
     * 
     * WHEN a new movie with an already existing ID is added to the database
     * THEN the movie is not added to the database.
     */
    @Test
    public void addMovieTest() {
        Database db = Database.getInstance();

        db.add(new Movie(0, "Movie 1", "Plot 1", "Genre 1", "2015-01-01", 100, 5.0));
        db.add(new Movie(1, "Movie 2", "Plot 2", "Genre 2", "2015-01-02", 200, 6.0));
        db.add(new Movie(0, "Movie 4", "Plot 4", "Genre 4", "2015-01-04", 400, 8.0));

        assertEquals(2, db.getMovies().size());
        assertNotEquals(null, db.getMovies().get(0));

        db.destroy();
    }

    /*
     * GIVEN a database with directors in it
     * 
     * WHEN a new director is added to the database
     * THEN the director is added to the database;
     * 
     * WHEN a new director with an already existing ID is added to the database
     * THEN the director is not added to the database;
     */
    @Test
    public void addDirectorTest() {
        Database db = Database.getInstance();

        db.add(new Director(0, "Director 1"));
        db.add(new Director(1, "Director 2"));
        db.add(new Director(0, "Director 4"));

        assertEquals(2, db.getDirectors().size());
        assertNotEquals(null, db.getDirectors().get(0));

        db.destroy();
    }

    /*
     * GIVEN a database with actors and movies in it
     * 
     * WHEN a new ActorMovie relation is added to the database
     * THEN the relation is added to the database;
     * 
     * WHEN an ActorMovie relation with either a non-existant actor or movie is
     * added,
     * THEN the relation is not added to the database.
     */
    @Test
    public void addActorMovieTest() {
        Database db = Database.getInstance();
        Actor actorNotInDatabase = new Actor(2, "Actor 3");
        Movie movieNotInDatabase = new Movie(2, "Movie 3", "Plot 3", "Genre 3", "2015-01-03", 300, 7.0);

        db.add(new Actor(0, "Actor 1"));
        db.add(new Actor(1, "Actor 2"));
        db.add(new Movie(0, "Movie 1", "Plot 1", "Genre 1", "2015-01-01", 100, 5.0));
        db.add(new Movie(1, "Movie 2", "Plot 2", "Genre 2", "2015-01-02", 200, 6.0));

        db.add(new ActorMovie(db.getActors().get(0), db.getMovies().get(0)));
        db.add(new ActorMovie(db.getActors().get(1), db.getMovies().get(1)));
        db.add(new ActorMovie(db.getActors().get(0), db.getMovies().get(1)));

        assertEquals(3, db.getActsInMovies().size());

        // add duplicate
        db.add(new ActorMovie(
                db.getActors().get(0),
                db.getMovies().get(0)));

        assertEquals(3, db.getActsInMovies().size());

        // add relations with non-existant entities
        db.add(new ActorMovie(actorNotInDatabase, db.getMovies().get(0)));
        db.add(new ActorMovie(db.getActors().get(0), movieNotInDatabase));
        assertEquals(3, db.getActsInMovies().size());

        db.destroy();
    }

    /*
     * GIVEN a database with directors and movies in it
     * 
     * WHEN a new DirectorMovie relation is added to the database
     * THEN the relation is added to the database;
     * 
     * WHEN a DirectorMovie relation with either a non-existant director or movie is
     * added,
     * THEN the relation is not added to the database.
     */
    @Test
    public void addDirectorMovieTest() {
        Database db = Database.getInstance();
        Director directorNotInDatabase = new Director(2, "Director 3");
        Movie movieNotInDatabase = new Movie(2, "Movie 3", "Plot 3", "Genre 3", "2015-01-03", 300, 7.0);

        db.add(new Director(0, "Director 1"));
        db.add(new Director(1, "Director 2"));
        db.add(new Movie(0, "Movie 1", "Plot 1", "Genre 1", "2015-01-01", 100, 5.0));
        db.add(new Movie(1, "Movie 2", "Plot 2", "Genre 2", "2015-01-02", 200, 6.0));

        db.add(new DirectorMovie(db.getDirectors().get(0), db.getMovies().get(0)));
        db.add(new DirectorMovie(db.getDirectors().get(1), db.getMovies().get(1)));
        db.add(new DirectorMovie(db.getDirectors().get(0), db.getMovies().get(1)));

        assertEquals(3, db.getDirectsMovies().size());

        // add duplicate
        db.add(new DirectorMovie(
                db.getDirectors().get(0),
                db.getMovies().get(0)));

        assertEquals(3, db.getDirectsMovies().size());

        // add relations with non-existant entities
        db.add(new DirectorMovie(directorNotInDatabase, db.getMovies().get(0)));
        db.add(new DirectorMovie(db.getDirectors().get(0), movieNotInDatabase));

        assertEquals(3, db.getDirectsMovies().size());

        db.destroy();

    }

}
