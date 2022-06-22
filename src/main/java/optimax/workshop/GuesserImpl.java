package optimax.workshop;

import java.util.Collection;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.guesser.Guesser;

/**
 * The {@link Guesser} represents a strategy for making guesses in the Wordle Game
 * based on an initial solution set, accepted set and multiple feedbacks from previous guesses.
 */
public class GuesserImpl implements Guesser {

    /**
     * Provides the {@code Guesser} with solution set and accepted set of words.
     *
     * @see Guesser#init
     */
    @Override
    public void init(Collection<Word> solutions, Collection<Word> accepted) {

    }

    /**
     * Computes the next valid guess.
     *
     * @see Guesser#nextGuess
     */
    @Override
    public Word nextGuess() {
        return null;
    }

    /**
     * Provides the {@code Guesser} with the feedback for the previously submitted guess
     *
     * @see Guesser#match
     */
    @Override
    public void match(Word guess, MatchResult result) {

    }

    /**
     * Helper method to check, whether the given string can be converted to {@link Word}
     */
    private static boolean isWordValid(String word) {
        return Word.isValid(word);
    }
}
