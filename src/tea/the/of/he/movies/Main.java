package tea.the.of.he.movies;


public class Main {

    public static Database db = Database.getInstance();

    public static void main(String[] args) {

        for(String arg : args) {
            System.out.println(arg);
        }
        
        MovieDbReader.readFromFile("src/movieproject2024.db");

        System.out.println("Actors: " + db.getActors().size());
        System.out.println("Movies: " + db.getMovies().size());
        System.out.println("Directors: " + db.getDirectors().size());
        System.out.println("ActorMovie: " + db.getActsInMovies().size());
        System.out.println("DirectorMovie: " + db.getDirectsMovies().size());
    }

}