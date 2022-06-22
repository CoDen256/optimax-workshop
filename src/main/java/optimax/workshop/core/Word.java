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

    /** The intern string representation of the word */
    private final String word;
    /** The containing letters of the word */
    private final List<Character> letters = new ArrayList<>();

    public Word(String input) {
        verifyWord(input);
        word = input.toLowerCase();
        word.chars().forEach(letter -> letters.add(verifyLetter((char) letter, word)));
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

    /**
     * Returns the letter at given position. The letter is a lowercase alphabetic character.
     *
     * @param index
     *         the position of the letter in the word
     * @return the letter
     */
    public char letter(int index) {
        return word.charAt(index);
    }

    /**
     * Returns all the 5 letters as an array, with the same order as in the word. Each letter is a lowercase alphabetic character
     *
     * @return the 5 letters
     */
    public char[] lettersAsArray() {
        return word.toCharArray();
    }

    /**
     * Returns all the 5 letters with the same order as in the word. Each letter is a lowercase alphabetic character.
     *
     * @return the 5 letters
     */
    public List<Character> letters() {
        return Collections.unmodifiableList(letters);
    }

    /**
     * Checks whether the word contains the given letter. The check is CASE INSENSITIVE
     *
     * @param letter
     *         the letter to check, may be in any case.
     * @return {@code true} if the word contains the letter, {@code false} otherwise
     */
    public boolean contains(char letter) {
        return letters.contains(Character.toLowerCase(letter));
    }

    /**
     * Checks whether the word contains the given char sequence. The check is CASE INSENSITIVE
     *
     * @param charSequence
     *         the char sequence. to check, may be in any case.
     * @return {@code true} if the word contains the char sequence, {@code false} otherwise
     */
    public boolean contains(String charSequence) {
        return word.contains(charSequence.toLowerCase());
    }

    /**
     * Returns the word as a string representation
     *
     * @return the word
     */
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

    /**
     * Checks whether the given letter is a valid Wordle Letter
     *
     * @param c
     *         the letter
     * @return {@code true} if valid {@code false} otherwise
     */
    public static boolean isValidLetter(char c) {
        return Character.isAlphabetic(c);
    }

    /**
     * Checks whether the given string is a valid 5 letter Wordle Word. It is valid, if <br>
     * - The word is not {@code null}  <br>
     * - The word contains only valid letters <br>
     * - The word contains exactly 5 letters <br>
     *
     * @param word
     *         the string to check
     * @return {@code true} if valid {@code false} otherwise
     */
    public static boolean isValid(String word) {
        if (word == null || word.length() != 5) return false;
        for (char c : word.toCharArray())
            if (!isValidLetter(c))
                return false;
        return true;
    }

    /** This exception is thrown when the given input string does not contain exactly 5 letters */
    public static class InvalidWordLengthException extends RuntimeException {
        public InvalidWordLengthException(String message) {
            super(message);
        }

        public InvalidWordLengthException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    /** This exception is thrown when the given input letter is invalid */
    public static class InvalidLetterException extends RuntimeException {
        public InvalidLetterException(String message) {
            super(message);
        }

        public InvalidLetterException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /** This exception is thrown when the given input word string is invalid */
    public static class InvalidWordException extends RuntimeException {
        public InvalidWordException(String message) {
            super(message);
        }

        public InvalidWordException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
