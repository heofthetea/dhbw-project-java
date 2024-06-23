package tea.the.of.he.movies;

import java.util.ArrayList;
import java.util.List;

import tea.the.of.he.movies.entities.Actor;
import tea.the.of.he.movies.entities.Movie;

public class Main {

    public static Database db = Database.getInstance();

    public static void main(String[] args) {

        MovieDbReader.readFromFile("movieproject2024.db");

        for (String arg : args) {
            System.out.println(arg);
        }

        System.out.println("Successfully read database file:");

        System.out.println("Actors: " + db.getActors().size());
        System.out.println("Movies: " + db.getMovies().size());
        System.out.println("Directors: " + db.getDirectors().size());
        System.out.println("ActorMovie: " + db.getActsInMovies().size());
        System.out.println("DirectorMovie: " + db.getDirectsMovies().size());
        System.out.println("---------------------------------------------------\n");

        String[] parameter = args[0].substring(2).split("=", 2); // remove the -- from the string
        db.getMoviesByActor(db.getActorByName("McDowall").get(0));

        switch (parameter[0]) {
            case "filmsuche":
                filmsuche(parameter[1]);
                break;
            case "schauspielersuche":
                schauspielersuche(parameter[1]);
                break;
            case "filmnetzwerk":
                filmnetzwerk(Integer.parseInt(parameter[1]));
                break;
            case "schauspielernetzwerk":
                schauspielernetzwerk(Integer.parseInt(parameter[1]));
                break;
            default:
                System.out.println("Invalid argument: " + args[0]);
                System.out.println("Valid arguments are: \n"
                        + "\t--filmsuche=<title: string | id: int> \n"
                        + "\t--schauspielersuche=<name: string | id: int> \n"
                        + "\t--schauspielernetzwerk=<id: int> \n"
                        + "\t--filmnetzwerk=<id: int> \n");

        }
    }

    public static void filmsuche(String title) {
        List<Movie> movies = db.getMovieByTitle(title);
        if (movies.isEmpty()) {
            System.out.println("No movies found with key word '" + title + "':");
        } else {
            System.out.println("Movies found for '" + title + "':\n");
            movies.stream().map(m -> "-" + m.toString()).forEach(System.out::println);
        }
    }

    public static void schauspielersuche(String name) {
        List<Actor> actors = db.getActorByName(name);
        if (actors.isEmpty()) {
            throw new RuntimeException("No actors found with key word '" + name + "':");
        } else {
            System.out.println("Actors found for '" + name + "':\n");
            actors.stream().map(a -> "-" + a.toString()).forEach(System.out::println);
        }
    }

    /**
     * 
     * <em>Displays</em> the movie network for a passed id, adhering to the
     * requirements described in <code>Anforderungen_java_projekt.pdf</code>.
     * The method uses {@link Database#getMoviesByActor(Actor)} and
     * {@link Database#getActorsByMovie(Movie)} to obtain all necessary data. The
     * only handled here is the printing and formatting.
     * 
     * The name of each actor playing in the movie is displayed. Then, for each of
     * those actors, the title of every movie he plays in is displayed.
     * It is ensured that no entry is printed twice.
     * 
     * 
     * @param id Movie id to query for
     */
    public static void filmnetzwerk(Integer id) {
        Movie queryMovie = db.getMovies().get(id); // TODO rename for readability?
        List<Actor> actors = db.getActorsByMovie(queryMovie); // represents level 1 of the network
        if (actors.isEmpty()) {
            throw new RuntimeException("No actors found for movie with id '" + id + "':");
        } else {
            System.out.println("Movie network vor movie " + db.getMovies().get(id).getTitle() + ":\n");
            System.out.print("Schauspieler: ");
            // format output of actors
            // The use of a for loop through all but the last element and the manual
            // printing of the last element
            // ensures that the printed line does not terminate with a comma.
            for (int i = 0; i < actors.size() - 1; i++) {
                System.out.print(actors.get(i).getName() + ", ");
            }
            System.out.println(actors.get(actors.size() - 1).getName());
        }

        List<Movie> movies = new ArrayList<Movie>(); // represents level 2 of the network
        for (Actor actor : actors)
            movies.addAll(db.getMoviesByActor(actor));
        // 1. remove all duplicates spawned by multiple actors starring in the same film
        // 2. remove the movie initially queried for
        movies = movies.stream().distinct().filter(m -> !m.equals(queryMovie)).toList();

        // format output of movies
        System.out.print("\nFilme: ");
        for (int i = 0; i < movies.size() - 1; i++) {
            System.out.print(movies.get(i).getTitle() + ", ");
        }
        System.out.println(movies.get(movies.size() - 1).getTitle());

    }

    /**
     * <em>Displays</em> the actor network for a passed id, adhering to the
     * requirements described in <code>Anforderungen_java_projekt.pdf</code>.
     * The method uses {@link Database#getMoviesByActor(Actor)} and
     * {@link Database#getActorsByMovie(Movie)} to obtain all necessary data. The
     * only thing handled here is the printing and formatting.
     * 
     * The name of each movie the actor plays in is displayed. Then, for each of
     * those movies, the name of every actor playing in it is displayedI.
     * It is ensured that no entry is printed twice.
     * 
     * 
     * @param id Actor id to query for
     */
    public static void schauspielernetzwerk(Integer id) {
        Actor queryActor = db.getActors().get(id);
        List<Movie> movies = db.getMoviesByActor(queryActor); // level 1 of the network
        if (movies.isEmpty()) {
            throw new RuntimeException("No movies found for actor with id '" + id + "':");
        } else {
            // format output for movies
            System.out.println("Actor network for actor " + db.getActors().get(id).getName() + ":\n");
            System.out.print("Filme: ");
            for (int i = 0; i < movies.size() - 1; i++) {
                System.out.print(movies.get(i).getTitle() + ", ");
            }
            System.out.println(movies.get(movies.size() - 1).getTitle());
        }
        List<Actor> actors = new ArrayList<Actor>(); // level 2 of the network
        for (Movie movie : movies) {
            actors.addAll(db.getActorsByMovie(movie));
        }
        // remove all duplicates and the actor initially queried for
        actors = actors.stream().distinct().filter(a -> !a.equals(queryActor)).toList();

        // format output for actors
        System.out.print("\nSchauspieler: ");
        for (int i = 0; i < actors.size() - 1; i++) {
            System.out.print(actors.get(i).getName() + ", ");
        }
        System.out.println(actors.get(actors.size() - 1).getName());
    }

}