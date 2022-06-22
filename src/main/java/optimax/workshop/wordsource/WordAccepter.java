package optimax.workshop.wordsource;

import optimax.workshop.core.Word;
import optimax.workshop.guesser.Guesser;

/**
 * A {@code WordAccepter} tells whether the given {@link Word} will be accepted
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
@FunctionalInterface
public interface WordAccepter {
    /**
     * Tells whether the given submitted {@link Word} guess is allowed. It is called
     * each time the {@link Guesser#nextGuess()} is called to produce next guess.
     *
     * @param word
     *         the word to check
     */
    boolean isAccepted(Word word);

    /**
     * Helper function to invert the {@link WordAccepter#isAccepted} behavior
     */
    default boolean isNotAccepted(Word word) {
        return !isAccepted(word);
    }

    /** Helper method to distinguish acceptors. For example used when displaying stats */
    default String name() {
        return getClass().getSimpleName();
    }
}
