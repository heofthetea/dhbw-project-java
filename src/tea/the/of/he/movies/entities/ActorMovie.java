package tea.the.of.he.movies.entities;

public record ActorMovie(Actor actor, Movie movie) {


    /**
     * Checks two Actor/Movie Relationships for equality.
     * Makes use of the equals methods implemented in Actor and Movie.
     * {@link Actor#equals(Object)}
     * {@link Movie#equals(Object)}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ActorMovie actorMovie)) {
            return false;
        }

        return actorMovie.actor.equals(this.actor) &&
                actorMovie.movie.equals(this.movie);
    }

    //-------------------------------------------------------------------------------------------------
}