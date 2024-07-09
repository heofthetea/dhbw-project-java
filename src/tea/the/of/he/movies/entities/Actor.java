package tea.the.of.he.movies.entities;

import java.util.Objects;

public class Actor {
    private final Integer id;
    private String name;

    public Actor(int id, String name) {
        this.id = id;
        this.name = name;
    }


    /*
     * An Actor is equal to another as soon as the id or the name matches.
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Actor)) {
            return false;
        }
        Actor actor = (Actor) obj;

        return actor.id == this.id &&
                actor.name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return id.toString() + " " + name;
    }













    // -------------------------------------------------------------------------------------------------


    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}