package optimax.workshop.core.match;

import optimax.workshop.core.Word;

/**
 * The {@code WordMatcher} compares two words and computes {@link  MatchResult} based on the amount
 * of correctly guessed letters. The matches in the {@link MatchResult} are computed in the relation to the {@code actual}
 * word. Represents a word matching strategy.
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
@FunctionalInterface
public interface WordMatcher {
    /**
     * Compares two words and computes {@link Match}es for each letter in the {@code actual} word
     * compared to the corresponding letters of the {@code expected} word
     *
     * @param expected
     *         the original/target/solution word, to be compared with
     * @param actual
     *         the actual/guess word, that is being compared
     * @return {@link MatchResult} containing 5 {@link Match}es for each letter in the {@code actual} word
     */
    MatchResult match(Word expected, Word actual);
}
