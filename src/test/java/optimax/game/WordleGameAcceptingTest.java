package optimax.game;

import static optimax.game.WordleGameTestUtils.word;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleGameAcceptingTest {

    @Test
    void givenAccepter_GameAcceptsOnlyWordsFromAccepter() {
        WordleGame game = new WordleGame(word("valid"), s -> s.getLetters()[0] == 'v');
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("xxxxx")));
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("abcde")));
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("fghjk")));
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("YYYYY")));
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("ZZZZZ")));
        assertDoesNotThrow(() -> game.submit(word("Vbcde")));
        assertDoesNotThrow(() -> game.submit(word("vbcde")));
        assertDoesNotThrow(() -> game.submit(word("vaaaa")));
        assertDoesNotThrow(() -> game.submit(word("valis")));
        assertDoesNotThrow(() -> game.submit(word("VVVVV")));
    }


    @Test
    void givenFalseAccepter_GameAcceptsNoWordExceptForSolution() {
        Word solution = word("valid");
        WordleGame game = new WordleGame(solution, solution::equals);
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("xxxxx")));
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("abcde")));
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("fghjk")));
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("YYYYY")));
        assertThrows(IllegalArgumentException.class, () -> game.submit(word("ZZZZZ")));
        assertDoesNotThrow(() -> game.submit(solution));
    }

    @Test
    void givenTrueAccepter_GameAcceptsAnyWord() {
        WordleGame game = new WordleGame(word("valid"), s -> true);
        assertDoesNotThrow(() -> game.submit(word("xxxxx")));
        assertDoesNotThrow(() -> game.submit(word("abcde")));
        assertDoesNotThrow(() -> game.submit(word("fghjk")));
        assertDoesNotThrow(() -> game.submit(word("YYYYY")));
        assertDoesNotThrow(() -> game.submit(word("ZZZZZ")));
    }
}
