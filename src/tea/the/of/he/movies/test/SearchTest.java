
package tea.the.of.he.movies.test;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import tea.the.of.he.movies.entities.*;

import tea.the.of.he.movies.Database;


/**
 * NOTE: This only unit tests the relevant getXByAttribute methods.
 * Integration testing for the Movie/Actor network is done by {@link ProjektTester} either way,
 * so there's some coverage there.
 */
public class SearchTest {

    /**
     * GIVEN a database with actors in it
     * WHEN queried for a name
     * THEN all actors with that name, and not one more, are returned.
     */
    @Test
    public void getActorsByNameTest() {
        Database db = Database.getInstance();

        Actor actor1 = new Actor(0, "Actor 1");
        Actor actor2 = new Actor(1, "actor 2"); // query should ignore case
        Actor actor3 = new Actor(2, "Actor 3");
        Actor actor4 = new Actor(3, "Schauspieler 4"); // should not match




        db.add(actor1);
        db.add(actor2);
        db.add(actor3);
        db.add(actor4);

        List<Actor> actors = db.getActorsByName("Actor");
        
        assertEquals(3, actors.size());
        assertTrue(actors.contains(actor1));
        assertTrue(actors.contains(actor2));
        assertTrue(actors.contains(actor3));
        assertFalse(actors.contains(actor4));

        db.destroy();
    }


    /**
     * GIVEN a database with movies in it
     * WHEN queried for a title
     * THEN all movies with that title, and not one more, are returned.
     */
    @Test
    public void getMoviesByTitleTest() {
        Database db = Database.getInstance();

        Movie movie1 = new Movie(0, "Movie 1", "Plot 1", "Genre 1", "2015-01-01", 100, 5.0);
        Movie movie2 = new Movie(1, "movie 2", "Plot 2", "Genre 2", "2015-01-02", 200, 6.0);
        Movie movie3 = new Movie(2, "Movie 3", "Plot 3", "Genre 3", "2015-01-03", 300, 7.0);
        Movie movie4 = new Movie(3, "Film 4", "Plot 4", "Genre 4", "2015-01-04", 400, 8.0);

        db.add(movie1);
        db.add(movie2);
        db.add(movie3);
        db.add(movie4);

        List<Movie> movies = db.getMoviesByTitle("movie");

        assertEquals(3, movies.size());
        assertTrue(movies.contains(movie1));
        assertTrue(movies.contains(movie2));
        assertTrue(movies.contains(movie3));
        assertFalse(movies.contains(movie4));

        db.destroy();
    }
}
