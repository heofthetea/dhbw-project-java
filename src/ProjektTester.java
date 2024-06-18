import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class ProjektTester {
    // Konfiguration eurer Hauptklasse. Bitte vollqualifizierten Klassennamen plus Paket angeben.
    // Z.b. die Klasse MeinProjekt im Paket dhbw.java, muss lauten: 'dhbw.java.MeinProjekt'
    private static final String MAIN_CLASS = "Main";

    public static void main(String[] args) {
        // Tests are passing
        boolean passed = true;

        // Battle for the Planet of the Apes
        passed = passedTestNetzwerk("--filmnetzwerk=2047", "Claude Akins", "Lassie Come Home", 3, 4);
        // Back to the Future Part
        passed &= passedTestNetzwerk("--filmnetzwerk=1592", "Michael J. Fox", "Back to the Future Part III", 3, 35);
        // Asterix and the Gauls
        passed &= passedTestNetzwerk("--filmnetzwerk=5764", "Jacques Morel", "Asterix and the Vikings ", 3, 0);
        // Tom Murray
        passed &= passedTestNetzwerk("--schauspielernetzwerk=9905", "Charles Chaplin", "Gold Rush", 2, 1);
        // Eric Clapton
        passed &= passedTestNetzwerk("--schauspielernetzwerk=15729", "Joe Brown", "Concert for George", 5, 2);
        // Bud Spencer
        passed &= passedTestNetzwerk("--schauspielernetzwerk=14230", "Marina Langner", "Banana Joe", 2, 0);

        if (passed) {
            System.out.println("Alle Tests bestanden ᕕ( ᐛ )ᕗ");
        } else {
            System.out.println("Leider nicht alle Tests bestanden.");
        }
    }

    /**
     * Leider war ich bei der Formulierung der Aufgabe etwas unpräzise, daher muss an dieser Stelle die Anzahl der Kommas gezählt werden, was nicht unbedingt der Anzahl an
     * Filmen entspricht, e.g. "Matrix, The".
     *
     * @param arg Programmargument
     * @param schauspielerContaines String, welcher in der Schauspielerzeile vorkommen muss
     * @param filmeContains String, welcher in der Filmzeile vorkommen muss
     * @param schauspielerComma Anzahl der Kommas in der Schauspielerzeile
     * @param filmeComma Anzahl der Kommas in der Filmzeile
     * @return
     */
    private static boolean passedTestNetzwerk(String arg, String schauspielerContaines, String filmeContains, int schauspielerComma, int filmeComma) {
        boolean passed = true;
        // Der System.out Stream muss umgebogen werden, damit dieser später überprüft werden kann.
        PrintStream normalerOutput = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        String[] args = {arg};
        try {
            // MainClass mittels Reflection bekommen und main Methode aufrufen
            Class<?> mainClass = Class.forName(MAIN_CLASS);
            Method mainMethod = mainClass.getDeclaredMethod("main", String[].class);
            mainMethod.invoke(null, (Object) args);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Main Klasse konnte nicht geladen werden, bitte Konfiguration prüfen.");
            System.exit(1);
        } finally {
            // System.out wieder zurücksetzen
            System.setOut(normalerOutput);
        }

        // Ergebnisse überprüfen.
        String matrixOutput = baos.toString();
        String[] lines = matrixOutput.split(System.lineSeparator());
        for (String line : lines) {
            if (line.startsWith("Schauspieler")) {
                if (!line.contains(schauspielerContaines)) {
                    passed = false;
                    System.err.println("Test: " + arg + " fehlgeschlagen, weil Schauspieler nicht stimmen.");
                }
                if (countComma(line) != schauspielerComma) {
                    passed = false;
                    System.err.println("Test: " + arg + " fehlgeschlagen, weil Anzahl der Schauspieler nicht stimmen. Erwartet: " + schauspielerComma + ", erhalten: " + countComma(line));
                }
            } else if (line.startsWith("Filme")) {
                if (!line.contains(filmeContains)) {
                    passed = false;
                    System.err.println("Test: " + arg + " fehlgeschlagen, weil Filme nicht stimmen.");
                }
                if (countComma(line) != filmeComma) {
                    passed = false;
                    System.err.println("Test: " + arg + " fehlgeschlagen, weil Anzahl der Filme nicht stimmen. Erwartet: " + filmeComma + ", erhalten: " + countComma(line));
                }
            }
        }
        return passed;
    }

    /**
     * Zählt das Auftreten von Kommas im gegebenen String.
     *
     * @param line
     * @return
     */
    private static int countComma(String line) {
        return line.length() - line.replace(",", "").length();
    }
}