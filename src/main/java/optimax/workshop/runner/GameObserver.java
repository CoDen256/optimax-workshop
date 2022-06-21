package optimax.workshop.runner;

import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.MatchResult;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface GameObserver {

    void onRunLaunched(int totalRuns);
    void onGameCreated(Word solution, Guesser guesser, WordAccepter accepter);
    void onGuessExpected();
    void onGuessSubmitted(Word guess, MatchResult result);
    void onGuessRejected(Word guess);
    void onGameFinished(boolean solved);
    void onRunFinished();
}
