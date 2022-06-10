package optimax.game.matcher;

import optimax.game.Word;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
@FunctionalInterface
public interface WordMatcher {
    MatchResult match(Word expected, Word actual);
}
