package tea.the.of.he.movies.entities;

import java.util.Objects;

public class ActorMovie {
    private final Actor actor;
    private final Movie movie;

    public ActorMovie(Actor actor, Movie movie) {
        this.actor = actor;
        this.movie = movie;
    }


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
        if (!(obj instanceof ActorMovie)) {
            return false;
        }
        ActorMovie actorMovie = (ActorMovie) obj;

        return actorMovie.actor.equals(this.actor) &&
                actorMovie.movie.equals(this.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actor, movie);
    }

    //-------------------------------------------------------------------------------------------------
    
    public Actor getActor() {
        return this.actor;
    }

    public Movie getMovie() {
        return this.movie;
    }
}