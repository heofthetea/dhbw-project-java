package de.heofthetea.movies;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    public void readFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line here
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
