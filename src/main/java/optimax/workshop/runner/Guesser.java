package optimax.workshop.runner;

import optimax.workshop.core.Word;
import optimax.workshop.core.dictionary.WordAccepter;
import optimax.workshop.core.dictionary.WordSource;
import optimax.workshop.core.matcher.MatchResult;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface Guesser {
    void init(WordSource source, WordAccepter accepter);
    Word nextGuess();

    void match(Word guess, MatchResult result);
}
