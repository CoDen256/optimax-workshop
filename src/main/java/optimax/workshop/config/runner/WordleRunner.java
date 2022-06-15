package optimax.workshop.config.runner;

import optimax.workshop.core.Word;
import optimax.workshop.core.WordleGame;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.GameRunner;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.WordAccepter;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordleRunner implements GameRunner {

    private final WordleGame game;
    private final GameObserver observer;
    private final Guesser guesser;
    private final WordAccepter accepter;

    public WordleRunner(WordleGame game, GameObserver observer, Guesser guesser, WordAccepter accepter) {
        this.game = game;
        this.observer = observer;
        this.guesser = guesser;
        this.accepter = accepter;

    }

    public void run(){
        observer.onCreated(game, guesser, accepter);
        while(!game.isFinished()){
            nextGuess();
        }
        observer.onFinished(game.isSolved());
    }


    private void nextGuess() {
        observer.onGuessExpected();
        Word guess = guesser.nextGuess();
        if (accepter.isNotAccepted(guess)){
            observer.onGuessRejected(guess);
            return;
        }
        MatchResult result = game.submit(guess);
        guesser.match(guess, result);
        observer.onGuessSubmitted(guess, result);
    }
}
