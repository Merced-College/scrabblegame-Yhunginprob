import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * ScrabbleGame
 *
 * Improvement: 
 * 1) Allow the user to exchange one of their letters before guessing.
 * 2) Enforce that the guess uses only the provided letters.
 * 3) Award points equal to the length of the valid word.
 */
public class ScrabbleGame {
    private static final String DICTIONARY_FILE = "CollinsScrabbleWords_2019.txt";
    private static final int NUM_LETTERS = 4;

    public static void main(String[] args) {
        List<Word> dictionary = loadDictionary();
        if (dictionary == null) {
            System.err.println("Could not load dictionary. Exiting.");
            return;
        }

        Collections.sort(dictionary);
        Scanner in = new Scanner(System.in);
        List<Character> rack = generateRack();

        System.out.println("Your letters: " + rack);
        // ——— Improvement: letter exchange ——————————————————————————————
        System.out.print("Exchange one letter? (yes/no): ");
        if (in.nextLine().trim().equalsIgnoreCase("yes")) {
            System.out.print("Which letter (enter one of " + rack + "): ");
            String toExchange = in.nextLine().trim().toUpperCase();
            if (toExchange.length() == 1 && rack.contains(toExchange.charAt(0))) {
                int idx = rack.indexOf(toExchange.charAt(0));
                char newLetter = (char) ('A' + new Random().nextInt(26));
                rack.set(idx, newLetter);
                System.out.println("New rack: " + rack);
            } else {
                System.out.println("Invalid choice; skipping exchange.");
            }
        }
        // ————————————————————————————————————————————————————————————————

        System.out.print("Make a word from those letters: ");
        String guess = in.nextLine().trim().toUpperCase();
        Word w = new Word(guess);

        // check uses only rack letters
        if (!usesOnlyRackLetters(guess, new ArrayList<>(rack))) {
            System.out.println("Your word uses letters not in your rack. Invalid!");
            return;
        }

        // binary search the sorted dictionary
        int pos = Collections.binarySearch(dictionary, w);
        if (pos >= 0) {
            System.out.println("Valid word! You score " + guess.length() + " point(s).");
        } else {
            System.out.println("Not a valid dictionary word.");
        }
    }

    private static List<Word> loadDictionary() {
        List<Word> dict = new ArrayList<>();
        try (Scanner fileIn = new Scanner(new File(DICTIONARY_FILE))) {
            while (fileIn.hasNextLine()) {
                String line = fileIn.nextLine().trim();
                if (!line.isEmpty()) {
                    dict.add(new Word(line));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return dict;
    }

    private static List<Character> generateRack() {
        List<Character> rack = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < NUM_LETTERS; i++) {
            rack.add((char) ('A' + rand.nextInt(26)));
        }
        return rack;
    }

    private static boolean usesOnlyRackLetters(String guess, List<Character> rack) {
        for (char c : guess.toCharArray()) {
            if (!rack.remove((Character) c)) {
                return false;
            }
        }
        return true;
    }
}
