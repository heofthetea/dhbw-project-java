package tea.the.of.he.movies.entities;

public class Director {
    private final int id;
    private String name;

    public Director(int id, String name) {
        this.id = id;
        this.name = name;
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

    /*
     * A Director is equal to another as soon as the id or the name matches.
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Director)) {
            return false;
        }
        Director director = (Director) obj;

        return director.id == this.id || director.name.equals(this.name);
    }


    @Override
    public int hashCode() {
        return name.hashCode();
    }
}