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

    //TODO remove lingering comma
    public static void filmnetzwerk(Integer id) {
        Movie query_movie = db.getMovies().get(id);
        List<Actor> actors = db.getActorsByMovie(query_movie);
        if(actors.isEmpty()) {
            System.out.println("No actors found for movie with id '" + id + "':");
        } else {
            System.out.println("Movie network vor movie " + db.getMovies().get(id).getTitle() + ":\n");
            System.out.print("Schauspieler: ");
            for (int i = 0; i < actors.size() - 1; i++) {
                System.out.print(actors.get(i).getName() + ", ");
            }
            System.out.println(actors.get(actors.size() - 1).getName());
        }
        List<Movie> movies = new ArrayList<Movie>();
        for(Actor actor : actors) {
            movies.addAll(db.getMoviesByActor(actor));
        }
        movies = movies.stream().distinct().filter(m -> !m.equals(query_movie)).toList();

        System.out.print("\nFilme: ");
        for (int i = 0; i < movies.size() - 1; i++) {
            System.out.print(movies.get(i).getTitle() + ", ");
        }
        System.out.println(movies.get(movies.size() - 1).getTitle());

    }

    public static void schauspielernetzwerk(Integer id) {
        Actor query_actor = db.getActors().get(id);
        List<Movie> movies = db.getMoviesByActor(query_actor);
        if(movies.isEmpty()) {
            System.out.println("No movies found for actor with id '" + id + "':");
        } else {
            System.out.println("Actor network for actor " + db.getActors().get(id).getName() + ":\n");
            System.out.print("Filme: ");
            for (int i = 0; i < movies.size() - 1; i++) {
                System.out.print(movies.get(i).getTitle() + ", ");
            }
            System.out.println(movies.get(movies.size() - 1).getTitle());
        }
        List<Actor> actors = new ArrayList<Actor>();
        for(Movie movie : movies) {
            actors.addAll(db.getActorsByMovie(movie));
        }
        actors = actors.stream().distinct().filter(a -> !a.equals(query_actor)).toList();

        System.out.print("\nSchauspieler: ");
        for (int i = 0; i < actors.size() - 1; i++) {
            System.out.print(actors.get(i).getName() + ", ");
        }
        System.out.println(actors.get(actors.size() - 1).getName());
    }


}