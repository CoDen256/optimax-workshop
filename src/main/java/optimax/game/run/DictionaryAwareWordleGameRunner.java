package optimax.game.run;

import java.util.Collection;
import java.util.function.Supplier;
import optimax.game.core.Word;
import optimax.game.core.WordleGame;
import optimax.game.core.matcher.MatchResult;
import optimax.game.run.guesser.DictionaryAwareGuesser;
import optimax.game.run.guesser.Guesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class DictionaryAwareWordleGameRunner extends GameRunner {

    private final DictionaryAwareGuesser guesser;
    private final Supplier<WordleGame> gameFactory;
    private final Collection<Word> dict;

    public DictionaryAwareWordleGameRunner(Supplier<WordleGame> gameFactory, Collection<Word> dict, DictionaryAwareGuesser guesser) {
        this.guesser = guesser;
        this.gameFactory = gameFactory;
        this.dict = dict;
    }


    @Override
    protected WordleGame createGame() {
        return null;
    }

    @Override
    protected Guesser createGuesser() {
        return null;
    }

    @Override
    protected void onCreated(WordleGame game, Guesser guesser) {

    }

    @Override
    protected void onFinished(WordleGame game, Guesser guesser) {

    }

    @Override
    protected void onSolved(WordleGame game, Guesser guesser) {

    }

    @Override
    protected void onGuessSubmitted(Word guess, MatchResult result) {

    }
}
