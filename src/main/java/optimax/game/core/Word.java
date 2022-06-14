package optimax.game.core;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a valid 5-letter Wordle word, that contains only alphabetic characters.
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public final class Word {

    private final String word;

    public Word(String word) {
        requireNonNull(word);
        if (word.length() != 5) {
            throw new IllegalArgumentException(
                    String.format("Word length must be 5, but was: %d (%s)", word.length(), word));
        }
        for (char c : word.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                throw new IllegalArgumentException(
                        String.format("Word must contain only alphabetic letters, but '%c' was found", c));
            }
        }
        this.word = word.toLowerCase();
    }

    public String word() {
        return word;
    }


    @Override
    public String toString() {
        return Arrays.stream(word.split("")).collect(Collectors.joining(",", "<", ">"));
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
}
