package optimax.workshop.run.observer;

import optimax.workshop.core.Word;
import optimax.workshop.score.GameSnapshot;

public interface GameStateObserver {
    void onGameCreated(GameSnapshot snapshot);
    void onGuessExpected(GameSnapshot snapshot);
    void onGuessSubmitted(GameSnapshot snapshot);
    void onGuessRejected(Word guess, GameSnapshot snapshot);
    void onGameFinished(GameSnapshot snapshot);
}
