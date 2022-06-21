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
public class AggregatedGameObserver implements GameObserver {

    private final List<GameObserver> observers;

    public AggregatedGameObserver(List<GameObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void onRunLaunched(int i) {
        observers.forEach(s -> s.onRunLaunched(i));
    }

    @Override
    public void onRunFinished() {
        observers.forEach(GameObserver::onRunFinished);

    }

    @Override
    public void onGameCreated(Word solution, Guesser guesser, WordAccepter accepter) {
        observers.forEach(o -> o.onGameCreated(solution, guesser, accepter));
    }

    @Override
    public void onGameFinished(boolean solved) {
        observers.forEach(o -> o.onGameFinished(solved));
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
