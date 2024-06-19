package tea.the.of.he.movies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tea.the.of.he.movies.entities.*;

/*
 * This class is already specifically implemented to handle the provided movieproject2024.db file.
 */
public class MovieDbReader {
    private static Database db = Database.getInstance();

    // This is the best solution I was able to come up with to handle switching to
    // reading a new entity,
    // and maintaining readibility. Cluttering The readFromFile method with a switch
    // would be borderline unreadable.

    private static final String[] ENTITIES = { "Actor", "Movie", "Director", "ActorMovie", "DirectorMovie" };

    /**
     * Reads a provided file line by line.
     * In doing so, checks for any occurance of 'New_Entity'.
     * uses {@link #readEntity(int, String)} to handle the actual logic behind
     * converting each line from String to a Java Object.
     */
    public static void readFromFile(String path){
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            int entity = -1;
            while ((line = reader.readLine()) != null) {

                // each time a New_Entity is encountered, the entity counter is incremented, and
                // the line ignored.
                if (line.startsWith("New_Entity")) {
                    entity++;
                    continue;
                }
                readEntity(entity, line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Database file not found. Ensure it exists in both the root project folder, as well as in src.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        removeDuplicates(db);
    }



    /**
     * Adapter for the individual reading methods.
     * Each entity is determined by the passed entity integer (in combination with the entities list), and 
     * the respective method is called to create the object. The returned Object is then added to the database.
     * @param entity Integer to identify the current entity from the 
     * @param line
     */
    private static void readEntity(int entity, String line) {
        switch (ENTITIES[entity]) {
            case "Actor":
                db.add(readActor(line));
                break;
            case "Movie":
                db.add(readMovie(line));
                break;
            case "Director":
                db.add(readDirector(line));
                break;
            case "ActorMovie":
                db.add(readActorMovie(line));
                break;
            case "DirectorMovie":
                db.add(readDirectorMovie(line));
                break;
        }
    }

    // yeah duck unit testing here ya go have yer method public ya little shift
    /**
     * Creates an Actor object from a String.
     * 
     * @param line
     * @return Actor instance
     */
    public static Actor readActor(String line) {
        String[] parts = line.split("\"");

        return new Actor(
                Integer.parseInt(parts[1]),
                parts[3].trim());
    }

    /**
     * Creates a Movie object from a String.
     * 
     * @param line
     * @return
     */
    public static Movie readMovie(String line) {
        String[] parts = line.split("\"");

        Integer imdbVotes = null;
        Double imdbRating = null;

        try {
            imdbVotes = Integer.parseInt(parts[11]);
            imdbRating = Double.parseDouble(parts[13]);
        } catch (NumberFormatException e) {
            // rating is not a mandatory field, meaning it might be an empty string in the db file.
            // If the parsing fails, the values are left as null.
        }

        // Splitting each line at the quotation character " Results in each actual data
        // field appearing in the odd indices
        // (as for some reason, the split() method returns an empty string in parts[0]
        // when the passed string starts with the splitting character.)
        // This allows for stupidly simple processing of each line, as attributes can
        // simply be read from the odd indices left to right.
        return new Movie(
                Integer.parseInt(parts[1]),
                parts[3].trim(),
                parts[5].trim(),
                parts[7].trim(),
                parts[9].trim(),
                imdbVotes,
                imdbRating);
    }

    /**
     * Creates a Director object from a String.
     * 
     * @param line
     * @return
     */
    public static Director readDirector(String line) {
        String[] parts = line.split("\"");

        return new Director(
                Integer.parseInt(parts[1]),
                parts[3].trim());
    }

    /**
     * Creates an ActorMovie object from a String.
     * The respective Objects to place as `actor` and `movie` are retrieved via
     * their id from the already existing database hashmaps.
     * 
     * Note that by the time this method is called, all Actors, Movies and Directors
     * should already be present in the database.
     * If that's not the case, some relations might not be instantiated correctly
     * because of a missing key in the hash map.
     * 
     * @param line
     * @return ActorMovie instance
     */
    private static ActorMovie readActorMovie(String line) {
        String[] parts = line.split("\"");

        Actor actor = db.getActors().get(Integer.parseInt(parts[1]));
        Movie movie = db.getMovies().get(Integer.parseInt(parts[3]));

        return new ActorMovie(actor, movie);
    }

    /**
     * Creates a DirectorMovie object from a String.
     * The respective Objects to place as `director` and `movie` are retrieved via
     * their id from the already existing database hashmaps.
     * 
     * Note that by the time this method is called, all Actors, Movies and Directors
     * should already be present in the database.
     * If that's not the case, some relations might not be instantiated correctly
     * because of a missing key in the hash map.
     * 
     * @param line
     * @return DirectorMovie instance
     */
    public static DirectorMovie readDirectorMovie(String line) {
        String[] parts = line.split("\"");

        Director director = db.getDirectors().get(Integer.parseInt(parts[1]));
        Movie movie = db.getMovies().get(Integer.parseInt(parts[3]));

        return new DirectorMovie(director, movie);
    }

    /**
     * Removes <em>all</em> duplicate Entries from the database.
     * This includes Actors, Movies, Directors, and their respective relations.
     * 
     * Duplicates are removed using the Stream API and the {@link Stream#distinct()}
     * method, uitilizing the
     * respective Classes implementation of the {@link Object#hashCode()} and
     * {@link Object#equals(Object)} methods.
     * 
     * <br>
     * It is always the first occurrance of an object which is kept.
     * 
     * @param db
     */

    public static void removeDuplicates(Database db) {
        db.setActors(
                db.getActors().values().stream()
                        .distinct()
                        .collect(Collectors.toMap(Actor::getId, actor -> actor)));

        db.setMovies(
                db.getMovies().values().stream()
                        .distinct()
                        .collect(Collectors.toMap(Movie::getId, movie -> movie)));

        db.setDirectors(
                db.getDirectors().values().stream()
                        .distinct()
                        .collect(Collectors.toMap(Director::getId, director -> director)));

        // there cannot be any duplicate relations
    }
}
