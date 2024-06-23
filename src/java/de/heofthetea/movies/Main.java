package de.heofthetea.movies;

import java.util.HashMap;

import de.heofthetea.movies.entities.Actor;

public class Main {
    public static void main(String[] args) {
        MovieDbReader.readFromFile("movieproject2024.db");
        Database db = Database.getInstance();


        MovieDbReader.removeDuplicates(db);
    }

}