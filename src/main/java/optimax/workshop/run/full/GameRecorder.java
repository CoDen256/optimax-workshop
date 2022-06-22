package optimax.workshop.run.full;

import java.util.ArrayList;
import java.util.List;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.guesser.Guesser;
import optimax.workshop.run.multiple.AggregatedSnapshot;
import optimax.workshop.run.single.GameSnapshot;
import optimax.workshop.wordsource.WordAccepter;
import optimax.workshop.run.multiple.AppObserver;
import optimax.workshop.run.single.GameObserver;

/**
 * A {@code GameRecorder} is a {@link FullLifecycleObserver}, that
 * records all the information and actions performed in the game. <br>
 * It collects all the metrics, statistics and submissions in a single
 * {@link GameSnapshot} <br>
 * A collection of {@link GameSnapshot}s is then combined to one single
 * {@link AggregatedSnapshot}
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class GameRecorder implements FullLifecycleObserver {

    private final List<GameSnapshot> recordedSnapshots = new ArrayList<>();
    private final GameObserver observer;
    private final AppObserver appObserver;
    private GameSnapshot currentSnapshot;
    private int currentGame = 0;


    public GameRecorder(AppObserver appObserver,
                        GameObserver observer) {
        this.observer = observer;
        this.appObserver = appObserver;
    }

    @Override
    public void onGameCreated(Word solution, Guesser guesser, WordAccepter accepter) {
        currentGame++;
        currentSnapshot = new GameSnapshot(solution, currentGame, System.currentTimeMillis(), guesser, accepter);
        observer.onGameCreated(currentSnapshot);
    }

    @Override
    public void onGameFinished(boolean solved) {
        currentSnapshot = currentSnapshot.setSolved(solved, System.currentTimeMillis());
        recordedSnapshots.add(currentSnapshot);
        observer.onGameFinished(currentSnapshot);
    }

    @Override
    public void onGuessExpected() {
        observer.onGuessExpected(currentSnapshot);
    }

    @Override
    public void onGuessSubmitted(Word guess, MatchResult result) {
        currentSnapshot = currentSnapshot.addSubmission(guess, result);
        observer.onGuessSubmitted(currentSnapshot);
    }

    @Override
    public void onGuessRejected(Word guess) {
        observer.onGuessRejected(guess, currentSnapshot);
    }

    @Override
    public void onLaunched(int totalRuns) {
        appObserver.onLaunched(totalRuns);
    }

    @Override
    public void onFinished() {
        appObserver.onFinished(new AggregatedSnapshot(recordedSnapshots));
    }
}
