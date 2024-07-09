package tea.the.of.he.movies.entities;

public record DirectorMovie(Director director, Movie movie) {


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
        if (!(obj instanceof DirectorMovie directorMovie)) {
            return false;
        }

        return directorMovie.director.equals(this.director) &&
                directorMovie.movie.equals(this.movie);
    }

}