package optimax.game.run;

import optimax.game.core.Word;
import optimax.game.core.WordleGame;
import optimax.game.core.matcher.MatchResult;
import optimax.game.run.guesser.Guesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public abstract class GameRunner {

    public void run(){
        WordleGame game = createGame();
        Guesser guesser = createGuesser();
        onCreated(game, guesser);
        while(!game.isFinished()){
            tryNextGuess(game, guesser);
        }
        onFinished(game, guesser);
        if (game.isSolved()){
            onSolved(game, guesser);
        }
    }

    private void tryNextGuess(WordleGame game, Guesser guesser) {
        try {
            nextGuess(game, guesser);
        }catch (Exception e){
            if (!handleError(e)){throw e;}
        }
    }

    private void nextGuess(WordleGame game, Guesser guesser) {
        onGuessExpected();
        Word guess = guesser.nextGuess();
        MatchResult result = game.submit(guess);
        guesser.match(guess, result);
        onGuessSubmitted(guess, result);
    }

    protected abstract WordleGame createGame();
    protected abstract Guesser createGuesser();
    protected abstract void onCreated(WordleGame game, Guesser guesser);
    protected abstract void onFinished(WordleGame game, Guesser guesser);
    protected abstract void onSolved(WordleGame game, Guesser guesser);
    protected abstract void onGuessExpected();
    protected abstract void onGuessSubmitted(Word guess, MatchResult result);
    protected abstract boolean handleError(Exception ex);
}
