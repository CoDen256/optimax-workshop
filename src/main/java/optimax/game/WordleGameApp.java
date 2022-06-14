package optimax.game;

import java.util.Set;
import optimax.game.core.Word;
import optimax.game.core.WordleGame;
import optimax.game.run.DictionaryAwareWordleGameRunner;
import optimax.game.run.guesser.Guesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordleGameApp {

    public static void main(String[] args) {
        Guesser guesser = new GuesserImpl();
        Set<Word> dict = Set.of(new Word("valid"), new Word("match"));
        WordleGame game = new WordleGame(6, new Word("valid"), new DictionaryAccepter(dict), new StandardMatcher());
        DictionaryAwareWordleGameRunner runner = new DictionaryAwareWordleGameRunner(guesser);
        runner.run(() -> game, dict);
    }


}
