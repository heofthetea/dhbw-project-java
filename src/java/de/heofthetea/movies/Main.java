package de.heofthetea.movies;

import java.util.HashMap;

import de.heofthetea.movies.entities.Actor;

public class Main {
    public static void main(String[] args) {
        MovieDbReader.readFromFile("movieproject2024.db");
        Database db = Database.getInstance();

        db.setActors(
            new HashMap<Integer, Actor>() {{
                put(1, new Actor(1, "John Doe"));
                put(2, new Actor(2, "Jane Doe"));
                put(3, new Actor(3, "John Doe"));
            }}
        );

        MovieDbReader.removeDuplicates(db);
    }

}