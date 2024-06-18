package tea.the.of.he.movies.test;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;

import tea.the.of.he.movies.MovieDbReader;
import tea.the.of.he.movies.entities.*;
import tea.the.of.he.movies.Database;


public class MovieDbReaderTest {

    /*
     * GIVEN a line containing the id and name of an actor
     * WHEN the line is read by the file reader
     * THEN a new actor is created with the correct id and name as attributes.
     */
    @Test
    public void readActorTest(){
        String line = "\"9817\",\" Jules-Eugène Legris\"";
        Actor actor = MovieDbReader.readActor(line);
        assertEquals(9817, actor.getId());
        assertEquals("Jules-Eugène Legris", actor.getName());

    }

    /*
     * GIVEN a line containing information about a movie
     * WHEN the line is read by the file reader
     * THEN a new movie is created with the correct attributes.
     */
    @Test
    public void readMovieTest(){
        String line = "\"8669\",\"Jupiter Ascending\",\"A young woman discovers her destiny as an heiress of intergalactic nobility and must fight to protect the inhabitants of Earth from an ancient and destructive industry.\",\"Adventure\",\"2015-02-06\",\"129577\",\"5.4\"";


        Movie movie = MovieDbReader.readMovie(line);
        assertEquals(8669, movie.getId());
        assertEquals("Jupiter Ascending", movie.getTitle());
        assertEquals("A young woman discovers her destiny as an heiress of intergalactic nobility and must fight to protect the inhabitants of Earth from an ancient and destructive industry.", movie.getPlot());
        assertEquals("Adventure", movie.getGenre());
        assertEquals("2015-02-06", movie.getReleased());
        assertEquals(129577, movie.getImdbVotes());
        assertEquals(5.4, movie.getImdbRating(), 0.01);
    }

    /*
     * GIVEN a line containing information about a director
     * WHEN the line is read by the file reader
     * THEN a new director is created with the correct attributes.
     */
    @Test
    public void readDirectorTest() {
        String line = "\"27982\",\" Paul Wegener\"";
        Director director = MovieDbReader.readDirector(line);
        assertEquals(27982, director.getId());
        assertEquals("Paul Wegener", director.getName());
    }

    /*
     * GIVEN a database with a list of actors containing a duplicate
     * WHEN the Database is passed to the removeDuplicates method
     * THEN the list of actors does not contain duplicates anymore.
     * 
     * Okay sometimes this GIVEN-WHEN-THEN thing might not be as useful as it seems
     */
    @Test
    public void removeDuplicateActorsTest() {
        Database db = Database.getInstance();

        db.setActors(
            new HashMap<Integer, Actor>() {{
                put(1, new Actor(1, "John Doe"));
                put(2, new Actor(2, "Jane Doe"));
                put(3, new Actor(3, "John Doe"));
            }}
        );

        MovieDbReader.removeDuplicates(db);

        assertEquals(2, db.getActors().size());
        assertNotEquals(null, db.getActors().get(1));

        db.destroy();

    }

    /*
     * GIVEN a database with a list of movies containing a duplicate
     * WHEN the Database is passed to the removeDuplicates method
     * THEN the list of movies does not contain duplicates anymore.
     */
    @Test
    public void removeDuplicateMoviesTest() {
        Database db = Database.getInstance();

        db.setMovies(
            new HashMap<Integer, Movie>() {{
                put(1, new Movie(1, "Movie 1", "Plot 1", "Genre 1", "2015-01-01", 100, 5.0));
                put(2, new Movie(2, "Movie 2", "Plot 2", "Genre 2", "2015-01-02", 200, 6.0));
                put(3, new Movie(3, "Movie 1", "Plot 1", "Genre 3", "2015-01-01", 100, 5.0));
            }}
        );

        MovieDbReader.removeDuplicates(db);

        assertEquals(2, db.getMovies().size());
        assertNotEquals(null, db.getMovies().get(1));

        db.destroy();
    }

    /*
     * GIVEN a database with a list of directors containing a duplicate
     * WHEN the Database is passed to the removeDuplicates method
     * THEN the list of directors does not contain duplicates anymore.
     */
    @Test
    public void removeDuplicateDirectorsTest() {
        Database db = Database.getInstance();

        db.setDirectors(
            new HashMap<Integer, Director>() {{
                put(1, new Director(1, "John Directoe"));
                put(2, new Director(2, "Jane Directoe"));
                put(3, new Director(3, "John Directoe"));
            }}
        );

        MovieDbReader.removeDuplicates(db);

        assertEquals(2, db.getDirectors().size());
        assertNotEquals(null, db.getDirectors().get(1));

        db.destroy();
    }

    

}
