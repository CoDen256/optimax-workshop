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
public class DictionaryAwareGameRunner extends GameRunner {

    private final Supplier<DictionaryAwareGuesser> guesserFactory;
    private final Supplier<WordleGame> gameFactory;
    private final Collection<Word> dict;

    public DictionaryAwareGameRunner(Supplier<WordleGame> gameFactory, Collection<Word> dict, Supplier<DictionaryAwareGuesser> guesser) {
        this.guesserFactory = guesser;
        this.gameFactory = gameFactory;
        this.dict = dict;
    }


    @Override
    protected WordleGame createGame() {
        return gameFactory.get();
    }

    @Override
    protected Guesser createGuesser() {
        DictionaryAwareGuesser guesser = guesserFactory.get();
        guesser.init(dict);
        return guesser;
    }

    @Override
    protected void onCreated(WordleGame game, Guesser guesser) {
        sout
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
