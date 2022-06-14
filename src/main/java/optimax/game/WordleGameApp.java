package optimax.game;

import java.util.Collection;
import java.util.Set;
import optimax.game.core.Word;
import optimax.game.core.WordleGame;
import optimax.game.run.DictionaryAwareGameRunner;
import optimax.game.run.GameRunner;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordleGameApp {

    public static void main(String[] args) {
        Word solution = createSolution();
        Collection<Word> dict = createDictionary(solution);
        GameRunner runner = createRunner(solution, dict);
        runner.run();
    }

    private static DictionaryAwareGameRunner createRunner(Word solution, Collection<Word> dict) {
        return new DictionaryAwareGameRunner(
                () -> createGame(solution, dict),
                dict,
                () -> new GuesserImpl()
        );
    }

    private static WordleGame createGame(Word solution, Collection<Word> dict) {
        return new WordleGame(6, solution, new DictionaryAccepter(dict), new StandardMatcher());
    }

    private static Word createSolution() {
        return new Word("valid");
    }

    private static Collection<Word> createDictionary(Word solution) {
        return Set.of(solution, new Word("match"));
    }


}
