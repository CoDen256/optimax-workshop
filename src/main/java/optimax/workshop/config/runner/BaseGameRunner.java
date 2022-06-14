package optimax.workshop.config.runner;

import optimax.workshop.core.Word;
import optimax.workshop.core.WordleGame;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.GameRunner;
import optimax.workshop.runner.Guesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public abstract class BaseGameRunner implements GameRunner {

    public void run(){
        WordleGame game = createGame();
        Guesser guesser = createGuesser();
        GameObserver observer = createObserver();

        observer.onCreated(game, guesser);
        while(!game.isFinished()){
            tryNextGuess(game, guesser, observer);
        }
        observer.onFinished();
        if (game.isSolved()){
            observer.onSolved();
        }
    }

    private void tryNextGuess(WordleGame game, Guesser guesser, GameObserver observer) {
        try {
            nextGuess(game, guesser, observer);
        }catch (Exception e){
            observer.onFailedGuess(e);
        }
    }

    private void nextGuess(WordleGame game, Guesser guesser, GameObserver observer) {
        observer.onGuessExpected();
        Word guess = guesser.nextGuess();
        MatchResult result = game.submit(guess);
        guesser.match(guess, result);
        observer.onGuessSubmitted(guess, result);
    }

    protected abstract WordleGame createGame();
    protected abstract Guesser createGuesser();
    protected abstract GameObserver createObserver();
}
