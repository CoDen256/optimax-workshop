package optimax.workshop.run.single;

import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.guesser.Guesser;
import optimax.workshop.wordsource.WordAccepter;

/**
 * A {@link GameLifecycleObserver} is an observer of a particular game instance running. It observes
 * the whole a lifecycle of a single game {@link SingleWordleRunner}
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface GameLifecycleObserver {
    /**
     * Called when a new game {@link SingleWordleRunner} is created
     *
     * @param solution
     *         the solution that was generated
     * @param guesser
     *         the guesser being used for guessing
     * @param accepter
     *         the accepter of the words
     */
    void onGameCreated(Word solution, Guesser guesser, WordAccepter accepter);

    /**
     * Called when a guess is expected from the {@link Guesser} via
     * {@link Guesser#nextGuess()}
     */
    void onGuessExpected();

    /**
     * Called when a <b>valid</b> guess is submitted
     *
     * @param guess
     *         the guess that was submitted
     * @param result
     *         the result of the match with the {@link  Word} solution
     */
    void onGuessSubmitted(Word guess, MatchResult result);

    /**
     * Called when a guess was invalid and it was rejected
     *
     * @param guess
     *         the rejected guess
     */
    void onGuessRejected(Word guess);

    /**
     * Called when the game was finished
     *
     * @param solved
     *         the final state of the game, tells whether the game was solved or failed
     */
    void onGameFinished(boolean solved);
}
