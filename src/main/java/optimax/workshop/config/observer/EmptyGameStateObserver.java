package optimax.workshop.config.observer;

import optimax.workshop.core.Word;
import optimax.workshop.runner.GameStateObserver;
import optimax.workshop.stats.GameSnapshot;

public class EmptyGameStateObserver implements GameStateObserver {

    @Override
    public void onGameCreated(GameSnapshot snapshot) {

    }

    @Override
    public void onGuessExpected(GameSnapshot snapshot) {

    }

    @Override
    public void onGuessSubmitted(GameSnapshot snapshot) {

    }

    @Override
    public void onGuessRejected(Word guess, GameSnapshot snapshot) {

    }

    @Override
    public void onGameFinished(GameSnapshot snapshot) {

    }
}
