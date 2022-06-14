package optimax.workshop.config.observer;

import optimax.workshop.core.Word;
import optimax.workshop.core.WordleGame;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.Guesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class ConsoleLogger implements GameObserver {
    @Override
    public void onCreated(WordleGame game, Guesser guesser) {
        System.out.printf("Worlde game has started with guesser `%s`%n", guesser.getClass().getSimpleName());
    }

    @Override
    public void onFinished() {
        System.out.printf("Worlde game has ended%n");
    }

    @Override
    public void onSolved() {
        System.out.printf("Worlde game was successfully solved!%n");
    }

    @Override
    public void onGuessExpected() {
        System.out.println("Expecting next guess");
    }

    @Override
    public void onGuessSubmitted(Word guess, MatchResult result) {
        System.out.printf("Guess: %s - %s%n", guess, result);
    }

    @Override
    public void onFailedGuess(Exception ex) {
        System.out.printf("Error: %s%n", ex.getMessage());
    }
}
