package tea.the.of.he.movies.entities;

import java.util.Objects;

public class Movie {
    private final Integer id;
    private String title;
    private String plot;
    private String genre;
    // Since the date is stored in yyyy-mm-dd, we can afford to store it as a
    // string, as alphabetical sorting results in the dates being in correct order.
    private String released; 
    private Integer imdbVotes;
    private Double imdbRating;

    public Movie(int id, String title, String plot, String genre, String released, Integer imdbVotes, Double imdbRating) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.genre = genre;
        this.released = released;
        this.imdbVotes = imdbVotes;
        this.imdbRating = imdbRating;
    }


    /*
     * Compares two movies for equality.
     * Two movies are equal when every one of their attributes match, even when their id is not equal.
     * If the id is equal, the object is immediately identified as equal.
     */

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Movie)) {
            return false;
        }
        
        Movie movie = (Movie) obj;

        if (movie.id == this.id) {
            return true;
        }

        return  movie.title.equals(this.title) &&
                movie.plot.equals(this.plot) &&
                movie.released.equals(this.released);
    }


    @Override
    public int hashCode() {
        return Objects.hash(title, plot, released);
    }


    @Override
    public String toString() {
        
        return id.toString() + "  " + title + ":" + 
            "\n\t - Plot: " + plot + "\n\t - Genre: " + genre + 
            "\n\t - Released: " + released + "\n\t - IMDB Votes: " + imdbVotes + "\n\t - IMDB Rating: " + imdbRating + "\n";
    }









    //-------------------------------------------------------------------------------------------------

    public int getId() {
        return this.id;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return this.plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleased() {
        return this.released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public int getImdbVotes() {
        return this.imdbVotes;
    }

    public void setImdbVotes(int imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public double getImdbRating() {
        return this.imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

}