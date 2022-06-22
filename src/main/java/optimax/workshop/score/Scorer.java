package optimax.workshop.score;

import java.util.ArrayList;
import java.util.List;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.run.guesser.Guesser;
import optimax.workshop.run.observer.FullObserver;
import optimax.workshop.run.observer.GameObserver;
import optimax.workshop.run.observer.GameStateObserver;
import optimax.workshop.run.words.WordAccepter;

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

    private long millisStarted = 0;

    public Scorer(GameStateObserver observer, FullObserver fullObserver) {
        this.observer = observer;
        this.fullObserver = fullObserver;
    }

    @Override
    public void onGameCreated(Word solution, Guesser guesser, WordAccepter accepter) {
        gameIndex++;
        gameSnapshot = new GameSnapshot(solution, gameIndex, guesser, accepter);
        observer.onGameCreated(gameSnapshot);
        millisStarted = System.currentTimeMillis();
    }

    @Override
    public void onGameFinished(boolean solved) {
        gameSnapshot = gameSnapshot.setSolved(solved, System.currentTimeMillis() - millisStarted);
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
        fullObserver.onRunFinished(new AggregatedSnapshot(recordedSnapshots));
    }
}
