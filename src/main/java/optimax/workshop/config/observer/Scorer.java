package optimax.workshop.config.observer;

import java.util.ArrayList;
import java.util.List;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.FullObserver;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.GameStateObserver;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.WordAccepter;
import optimax.workshop.stats.AggregatedSnapshots;
import optimax.workshop.stats.GameSnapshot;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class Scorer implements GameObserver {

    private final List<GameSnapshot> recordedSnapshots = new ArrayList<>();
    private final GameStateObserver observer;
    private final FullObserver fullObserver;
    private GameSnapshot gameSnapshot;
    private int gameIndex = 0;

    public Scorer(GameStateObserver observer, FullObserver fullObserver) {
        this.observer = observer;
        this.fullObserver = fullObserver;
    }

    @Override
    public void onGameCreated(Word solution, Guesser guesser, WordAccepter accepter) {
        gameIndex++;
        gameSnapshot = new GameSnapshot(solution, gameIndex, guesser, accepter);
        observer.onGameCreated(gameSnapshot);
    }

    @Override
    public void onGameFinished(boolean solved) {
        gameSnapshot = gameSnapshot.setSolved(solved);
        recordedSnapshots.add(gameSnapshot);
        observer.onGameFinished(gameSnapshot);
    }

    @Override
    public void onGuessExpected() {
        observer.onGuessExpected(gameSnapshot);
    }

    @Override
    public void onGuessSubmitted(Word guess, MatchResult result) {
        gameSnapshot = gameSnapshot.addSubmission(guess, result);
        observer.onGuessSubmitted(gameSnapshot);
    }

    @Override
    public void onGuessRejected(Word guess) {
        observer.onGuessRejected(guess, gameSnapshot);
    }

    @Override
    public void onRunLaunched(int totalRuns) {
        fullObserver.onRunLaunched(totalRuns);
    }
    @Override
    public void onRunFinished() {
        fullObserver.onRunFinished(new AggregatedSnapshots(recordedSnapshots));
    }
}
