package optimax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleTest {

    @Test
    void create_game_not_finished(){
        WordleGame game = new WordleGame();
        assertFalse(game.isFinished());
    }

    @Test
    void create_game_not_solved() {
        WordleGame game = new WordleGame();
        assertFalse(game.isSolved());
    }

    @Test
    void submit_word() {
        WordleGame game = new WordleGame();
//        game.submitGuess()
    }
}
