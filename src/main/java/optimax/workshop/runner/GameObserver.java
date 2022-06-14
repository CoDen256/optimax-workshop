package optimax.workshop.runner;

import optimax.workshop.core.Word;
import optimax.workshop.core.WordleGame;
import optimax.workshop.core.matcher.MatchResult;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface GameObserver {
    void onCreated(WordleGame game, Guesser guesser);
    void onFinished();
    void onSolved();
    void onGuessExpected();
    void onGuessSubmitted(Word guess, MatchResult result);
    void onFailedGuess(Exception ex);
}
