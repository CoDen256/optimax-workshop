package optimax.workshop.config.observer;

import java.util.List;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.WordAccepter;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class AggregatedObserver implements GameObserver {

    private final List<GameObserver> observers;

    public AggregatedObserver(List<GameObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void onCreated(Word solution, Guesser guesser, WordAccepter accepter) {
        observers.forEach(o -> o.onCreated(solution, guesser, accepter));
    }

    @Override
    public void onFinished(boolean solved) {
        observers.forEach(o -> o.onFinished(solved));
    }

    @Override
    public void onGuessExpected() {
        observers.forEach(o -> o.onGuessExpected());
    }

    @Override
    public void onGuessSubmitted(Word guess, MatchResult result) {
        observers.forEach(o -> o.onGuessSubmitted(guess, result));
    }

    @Override
    public void onGuessRejected(Word guess) {
        observers.forEach(o -> o.onGuessRejected(guess));
    }
}
