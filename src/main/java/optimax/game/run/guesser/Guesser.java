package optimax.game.run.guesser;

import optimax.game.core.Word;
import optimax.game.core.matcher.MatchResult;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface Guesser {

    Word nextGuess();

    void match(Word guess, MatchResult result);

    void finish();
}
