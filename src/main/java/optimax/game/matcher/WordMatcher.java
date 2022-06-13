package optimax.game.matcher;

import optimax.game.Word;

/**
 * The {@code WordMatcher} compares two words and computes {@link  MatchResult} based on the amount
 * of correctly guessed letters. The matches in the {@link MatchResult} are computed in the relation to the {@code actual}
 * word
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
@FunctionalInterface
public interface WordMatcher {
    MatchResult match(Word expected, Word actual);
}
