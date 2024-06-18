package tea.the.of.he.movies;

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


        switch (parameter[0]) {
            case "filmsuche":
                filmsuche(parameter[1]);
                break;
            case "schauspielersuche":
                schauspielersuche(parameter[1]);
                break;
            
        }


        // System.out.println(db.getMovies().get(0).toString());
    }


    public static void filmsuche(String title) {
        List<Movie> movies = db.getMovieByTitle(title);
        if(movies.isEmpty()) {
            System.out.println("No movies found with key word '" + title + "':");
        } else {
            System.out.println("Movies found for '" + title + "':\n");
            movies.stream().map(m -> "-" + m.toString()).forEach(System.out::println);
        }
    }

    public static void schauspielersuche(String name) {
        List<Actor> actors = db.getActorByName(name);
        if(actors.isEmpty()) {
            System.out.println("No actors found with key word '" + name + "':");
        } else {
            System.out.println("Actors found for '" + name + "':\n");
            actors.stream().map(a -> "-" + a.toString()).forEach(System.out::println);
        }
    }


}