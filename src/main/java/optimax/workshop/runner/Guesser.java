package optimax.workshop.runner;

import java.util.Collection;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.MatchResult;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface Guesser {
    void init(Collection<Word> solutions, Collection<Word> accepted);
    Word nextGuess();
    void match(Word guess, MatchResult result);
}
