package optimax.game;

import static optimax.game.WordleGameTestUtils.word;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordleGameTest {

    @Test
    void createNewGame() {
        assertDoesNotThrow(() -> new WordleGame(word("valid"), w -> true));
    }

    @Test
    void createNewGameWithNullSolutionFails() {
        assertThrows(NullPointerException.class, () -> new WordleGame(null, w -> true));
    }

    @Test
    void createNewGameWithNullAccepterFails() {
        assertThrows(NullPointerException.class, () -> new WordleGame(word("valid"), null));
    }

    @Test
    void createNewGameWithNotAcceptedSolutionFails() {
        assertThrows(IllegalArgumentException.class, () -> new WordleGame(word("valid"), w -> false));
    }
}
