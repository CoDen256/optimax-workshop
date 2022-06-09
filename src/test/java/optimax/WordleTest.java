package optimax;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleTest {

    @Test
    void createNewGame_GameIsNotFinished() {
        WordleGame game = createDefaultGame();
        assertFalse(game.isFinished());
    }

    @Test
    void createNewGame_GameIsNotSolved() {
        WordleGame game = createDefaultGame();
        assertFalse(game.isSolved());
    }

    @Test
    void submitNullFails() {
        WordleGame game = createDefaultGame();
        assertThrows(NullPointerException.class, () -> game.submit(null));
    }

    @Test
    void submitWord5Times_GameIsFinished() {
        WordleGame game = createDefaultGame();
        game.submit(new Word("xxxxx"));
        game.submit(new Word("valid"));
        game.submit(new Word("adult"));
        game.submit(new Word("pesto"));
        assertFalse(game.isFinished());
        game.submit(new Word("desto"));
        assertTrue(game.isFinished());
    }

    @Test
    void submitWordMoreThan5TimesFails() {
        WordleGame game = createDefaultGame();
        Word guess = new Word("xxxxx");
        game.submit(guess);
        game.submit(guess);
        game.submit(guess);
        game.submit(guess);
        game.submit(guess);
        assertThrows(IllegalStateException.class, () -> game.submit(guess));
        assertThrows(IllegalStateException.class, () -> game.submit(guess));
    }

    @Test
    void createNewGame_GetEmptySubmittedGuesses() {
        WordleGame game = createDefaultGame();
        assertThat(game.getSubmitted()).isEmpty();
    }

    @Test
    void submitGuess_GetSubmittedGuess() {
        WordleGame game = createDefaultGame();
        game.submit(new Word("xxxxx"));
        assertThat(game.getSubmitted()).containsExactly(new Word("xxxxx"));
    }

    @Test
    void submit2Guesses_Get2SubmittedGuesses() {
        WordleGame game = createDefaultGame();
        game.submit(new Word("xxxxx"));
        game.submit(new Word("yyyyy"));
        assertThat(game.getSubmitted()).containsExactly(new Word("xxxxx"), new Word("yyyyy"));
    }

    @Test
    void submit3Guesses_Get3SubmittedGuesses() {
        WordleGame game = createDefaultGame();
        game.submit(new Word("xxxxx"));
        game.submit(new Word("yyyyy"));
        game.submit(new Word("zzzzz"));
        assertThat(game.getSubmitted()).containsExactly(
                new Word("xxxxx"),
                new Word("yyyyy"),
                new Word("zzzzz")
        );
    }

    @Test
    void submit2SameGuesses_Get2SameSubmittedGuesses() {
        WordleGame game = createDefaultGame();
        game.submit(new Word("xxxxx"));
        game.submit(new Word("xxxxx"));
        assertThat(game.getSubmitted()).containsExactly(new Word("xxxxx"), new Word("xxxxx"));
    }

    @Test
    void submit5Guesses_Get5SubmittedGuesses() {
        WordleGame game = createDefaultGame();
        game.submit(new Word("xxxxx"));
        game.submit(new Word("aaaaa"));
        game.submit(new Word("bbbbb"));
        game.submit(new Word("ccccc"));
        game.submit(new Word("ddddd"));
        assertThat(game.getSubmitted()).containsExactly(
                new Word("aaaaa"),
                new Word("bbbbb"),
                new Word("ccccc"),
                new Word("ddddd"),
                new Word("xxxxx")
        );
    }

    @Test
    void submit5Guesses_Get5SubmittedGuessesInOrder() {
        WordleGame game = createDefaultGame();
        game.submit(new Word("aaaaa"));
        game.submit(new Word("bbbbb"));
        game.submit(new Word("ccccc"));
        game.submit(new Word("ddddd"));
        game.submit(new Word("eeeee"));
        assertThat(game.getSubmitted()).containsExactly(
                new Word("aaaaa"),
                new Word("bbbbb"),
                new Word("ccccc"),
                new Word("ddddd"),
                new Word("eeeee")
        ).inOrder();
    }

    @Test
    void submitMoreThan5Guesses_Get5SubmittedGuessesInOrder() {
        WordleGame game = createDefaultGame();
        game.submit(new Word("aaaaa"));
        game.submit(new Word("bbbbb"));
        game.submit(new Word("ccccc"));
        game.submit(new Word("ddddd"));
        game.submit(new Word("eeeee"));
        assertThrows(IllegalStateException.class, () -> game.submit(new Word("ggggg")));
        assertThrows(IllegalStateException.class, () -> game.submit(new Word("hhhhh")));
        assertThrows(IllegalStateException.class, () -> game.submit(new Word("iiiii")));
        assertThat(game.getSubmitted()).containsExactly(
                new Word("aaaaa"),
                new Word("bbbbb"),
                new Word("ccccc"),
                new Word("ddddd"),
                new Word("eeeee")
        ).inOrder();
    }



    private WordleGame createDefaultGame() {
        return new WordleGame();
    }

}
