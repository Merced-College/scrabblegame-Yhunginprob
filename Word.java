import java.util.Objects;

/**
 * Word object for ScrabbleGame.
 * Implements Comparable so we can sort and binary-search a list of Word.
 */
public class Word implements Comparable<Word> {
    private final String text;

    public Word(String text) {
        this.text = text.trim();
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(Word other) {
        // case-insensitive alphabetical order
        return this.text.compareToIgnoreCase(other.text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word other = (Word) o;
        return text.equalsIgnoreCase(other.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text.toLowerCase());
    }

    @Override
    public String toString() {
        return text;
    }
}
