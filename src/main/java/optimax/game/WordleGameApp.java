package optimax.game;

import java.util.Collection;
import java.util.Set;
import optimax.game.core.Word;
import optimax.game.core.WordleGame;
import optimax.game.core.accepter.WordAccepter;
import optimax.game.guesser.RandomGuesser;
import optimax.game.run.DictionaryAwareGameRunner;
import optimax.game.run.GameRunner;
import optimax.game.run.guesser.DictionaryAwareGuesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordleGameApp {

    public static void main(String[] args) {
        GameRunner runner = createRunner();
        runner.run();
    }

    private static GameRunner createRunner() {
        return new DictionaryAwareGameRunner(
                () -> game(),
                dictionary(),
                () -> guesser()
        );
    }

    private static DictionaryAwareGuesser guesser() {
        return new RandomGuesser();
    }

    private static WordleGame game() {
        return new WordleGame(maxAttempts(), solution(), accepter(), new StandardMatcher());
    }

    private static int maxAttempts() {
        return 6;
    }

    private static WordAccepter accepter() {
        return new DictionaryAccepter(dictionary());
    }

    private static Collection<Word> dictionary() {
        return Set.of(solution(), new Word("match"));
    }

    private static Word solution() {
        return new Word("valid");
    }
}
