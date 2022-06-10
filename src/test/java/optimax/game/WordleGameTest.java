package optimax.game;

import static optimax.game.TestUtilities.word;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import optimax.game.matcher.StandardMatcher;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleGameTest {

    @Test
    void createNewGame() {
        assertDoesNotThrow(() -> new WordleGame(word("valid"), w -> true, new StandardMatcher()));
    }

    @Test
    void createNewGameWithNullSolutionFails() {
        assertThrows(NullPointerException.class, () -> new WordleGame(null, w -> true, new StandardMatcher()));
    }

    @Test
    void createNewGameWithNullAccepterFails() {
        assertThrows(NullPointerException.class, () -> new WordleGame(word("valid"), null, new StandardMatcher()));
    }

    @Test
    void createNewGameWitNullComparerFails() {
        assertThrows(NullPointerException.class, () -> new WordleGame(word("valid"), w -> true, null));
    }

    @Test
    void createNewGameWithNotAcceptedSolutionFails() {
        assertThrows(IllegalArgumentException.class, () -> new WordleGame(word("valid"), w -> false, new StandardMatcher()));
    }

    @Test
    void givenAccepter_gameFailsIfNotAccepted() {
        WordleGame game = new WordleGame(word("valid"), s -> s.word().charAt(0) == 'v', new StandardMatcher());
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("abcde")));
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("fghjk")));
        assertDoesNotThrow(() -> game.submit(word("Vbcde")));
        assertDoesNotThrow(() -> game.submit(word("valis")));
        assertDoesNotThrow(() -> game.submit(word("VVVVV")));
    }


    @Test
    void givenFalseAccepter_gameFailsOnEverySubmitExceptSolution() {
        Word solution = word("valid");
        WordleGame game = new WordleGame(solution, solution::equals, new StandardMatcher());
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("xxxxx")));
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("ZZZZZ")));
        assertDoesNotThrow(() -> game.submit(solution));
    }

    @Test
    void givenTrueAccepter_gameAcceptsAnyWord() {
        WordleGame game = new WordleGame(word("valid"), s -> true, new StandardMatcher());
        assertDoesNotThrow(() -> game.submit(word("xxxxx")));
        assertDoesNotThrow(() -> game.submit(word("ZZZZZ")));
        assertDoesNotThrow(() -> game.submit(word("valid")));
    }
}
