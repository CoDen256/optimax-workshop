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
    private final List<Letter> letters = new ArrayList<>();

    public Word(String input) {
        verifyWord(input);
        word = input.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            char c = this.word.charAt(i);
            verifyLetter(c, i);
            letters.add(new Letter(c, i));
        }
    }

    private void verifyWord(String word) {
        requireNonNull(word);

        if (isNotFiveLetterWord(word))
            throw new IllegalArgumentException(
                    format("Word length must be 5, but was: %d (%s)", word.length(), word));
    }

    private void verifyLetter(char c, int i) {
        if (isInvalidLetter(c))
            throw new IllegalArgumentException(
                    format("Letter '%s' is not alphabetic in word <%s> at position %d", c, this.word, i));
    }

    public String word() {
        return word;
    }

    public char letter(int index) {
        return letters.get(index).getChar();
    }

    public List<Letter> letters() {
        return Collections.unmodifiableList(letters);
    }

    @Override
    public String toString() {
        return String.format("<%s>", word);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return word.equals(word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    private static boolean isNotFiveLetterWord(String word) {
        return word.length() != 5;
    }

    public static boolean isInvalidLetter(char c) {
        return !Character.isAlphabetic(c);
    }

    public static boolean isValid(String word) {
        if (word == null || isNotFiveLetterWord(word)) return false;
        for (char c : word.toCharArray())
            if (isInvalidLetter(c))
                return false;
        return true;
    }
}
