package tea.the.of.he.movies.entities;

import java.util.Objects;

public class DirectorMovie {
    private final Director director;
    private final Movie movie;

    public DirectorMovie(Director director, Movie movie) {
        this.director = director;
        this.movie = movie;
    }


    /**
    * Checks two Director/Movie Relationships for equality.
    * Makes use of the equals methods implemented in Director and Movie.
    * {@link Director#equals(Object)}
    * {@link Movie#equals(Object)}
    */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DirectorMovie)) {
            return false;
        }
        DirectorMovie directorMovie = (DirectorMovie) obj;

        return directorMovie.director.equals(this.director) &&
                directorMovie.movie.equals(this.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(director, movie);
    }

    //-------------------------------------------------------------------------------------------------
    
    public Director getDirector() {
        return this.director;
    }

    public Movie getMovie() {
        return this.movie;
    }
}