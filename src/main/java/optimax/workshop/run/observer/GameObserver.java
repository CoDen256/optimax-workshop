package optimax.workshop.run.observer;

import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.run.guesser.Guesser;
import optimax.workshop.run.words.WordAccepter;

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
