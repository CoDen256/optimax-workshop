package optimax.workshop.run.single;

import optimax.workshop.core.Word;
import optimax.workshop.run.full.GameRecorder;

/**
 * The {@code GameObserver} is an observer, that is similar to {@link GameLifecycleObserver},
 * but contains additional information about the game state, that is saved in the {@link GameSnapshot}.
 * The snapshots are being provided by the {@link GameRecorder}, containing all the necessary information
 * about current state of the game
 */
public interface GameObserver {
    /** @see GameLifecycleObserver#onGameCreated  */
    void onGameCreated(GameSnapshot snapshot);

    /** @see GameLifecycleObserver#onGuessExpected  */
    void onGuessExpected(GameSnapshot snapshot);

    /** @see GameLifecycleObserver#onGuessSubmitted */
    void onGuessSubmitted(GameSnapshot snapshot);

    /** @see GameLifecycleObserver#onGuessRejected  */
    void onGuessRejected(Word guess, GameSnapshot snapshot);

    /** @see GameLifecycleObserver#onGameFinished  */
    void onGameFinished(GameSnapshot snapshot);
}
