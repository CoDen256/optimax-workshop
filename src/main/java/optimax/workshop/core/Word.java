package optimax.workshop.core;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a valid 5-letter Wordle word, that contains only alphabetic characters.
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public final class Word {

    private final String word;
    private final List<Character> letters = new ArrayList<>();

    public Word(String input) {
        verifyWord(input);
        word = input.toLowerCase();
        word.chars().forEach( letter -> letters.add(verifyLetter((char)letter, word)));
    }

    private void verifyWord(String word) {
        requireNonNull(word);
        if (word.length() != 5)
            throw new InvalidWordLengthException(
                    format("Word length must be 5, but was: %d (%s)", word.length(), word));
    }

    private char verifyLetter(char c, String word) {
        if (!isValidLetter(c))
            throw new InvalidLetterException(
                    format("Letter '%s' is not valid in word (%s)", c, word));
        return c;
    }

    public char letter(int index) {
        return word.charAt(index);
    }

    public char[] lettersAsArray() {
        return word.toCharArray();
    }

    public List<Character> letters() {
        return Collections.unmodifiableList(letters);
    }

    public boolean contains(char letter){
        return letters.contains(Character.toLowerCase(letter));
    }

    public boolean contains(String letter){
        return word.contains(letter.toLowerCase());
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word other = (Word) o;
        return word.equals(other.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    public static boolean isValidLetter(char c) {
        return Character.isAlphabetic(c);
    }

    public static boolean isValid(String word) {
        if (word == null || word.length() != 5) return false;
        for (char c : word.toCharArray())
            if (!isValidLetter(c))
                return false;
        return true;
    }

    public static class InvalidWordLengthException extends RuntimeException{
        public InvalidWordLengthException(String message) {
            super(message);
        }

        public InvalidWordLengthException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InvalidLetterException extends RuntimeException{
        public InvalidLetterException(String message) {
            super(message);
        }

        public InvalidLetterException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InvalidWordException extends RuntimeException{
        public InvalidWordException(String message) {
            super(message);
        }

        public InvalidWordException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
