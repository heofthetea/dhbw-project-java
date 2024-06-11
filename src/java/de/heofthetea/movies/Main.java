package de.heofthetea.movies;


public class Main {
    public static void main(String[] args) {
        MovieDbReader.readFromFile("movieproject2024.db");
        Database db = Database.getInstance();
    }

}