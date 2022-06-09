package optimax;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleGameTest {

    @Test
    void createNewGame_GameIsNotFinished() {
        WordleGame game = newGame();
        assertFalse(game.isFinished());
    }

    @Test
    void createNewGame_GameIsNotSolved() {
        WordleGame game = newGame();
        assertFalse(game.isSolved());
    }

    @Test
    void submitNullFails() {
        WordleGame game = newGame();
        assertThrows(NullPointerException.class, () -> game.submit(null));
    }

    @Test
    void submitWord5Times_GameIsFinished() {
        WordleGame game = newGame("xxxxx", "yyyyy", "zzzzz");
        submit(game, "xxxxx", "yyyyy", "zzzzz", "xxxxx");
        assertFalse(game.isFinished());
        submit(game, "yyyyy");
        assertTrue(game.isFinished());
    }

    @Test
    void submitWordMoreThan5TimesFails() {
        WordleGame game = newGame("xxxxx");
        String guess = "xxxxx";
        // exercise
        submit(game, guess, guess, guess, guess, guess);
        // verify
        assertThrows(IllegalStateException.class, () -> submit(game, guess));
        assertThrows(IllegalStateException.class, () -> submit(game, guess));
    }

    @Test
    void createNewGame_GetEmptySubmittedGuesses() {
        WordleGame game = newGame();
        assertThat(game.getSubmitted()).isEmpty();
    }

    @Test
    void submitGuess_GetSubmittedGuess() {
        WordleGame game = newGame("xxxxx");
        submit(game, "xxxxx");
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"));
    }

    @Test
    void submit2Guesses_Get2SubmittedGuesses() {
        WordleGame game = newGame("xxxxx", "yyyyy");
        // exercise
        submit(game, "xxxxx", "yyyyy");
        // verify
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"), word("yyyyy")).inOrder();
    }

    @Test
    void submit3Guesses_Get3SubmittedGuesses() {
        WordleGame game = newGame("xxxxx", "yyyyy", "zzzzz");
        // exercise
        submit(game, "xxxxx", "yyyyy", "zzzzz");
        // verify
        assertThat(game.getSubmitted()).containsExactly(
                word("xxxxx"),
                word("yyyyy"),
                word("zzzzz")
        ).inOrder();
    }

    @Test
    void submit2SameGuesses_Get2SameSubmittedGuesses() {
        WordleGame game = newGame("xxxxx");
        // exercise
        submit(game, "xxxxx", "xxxxx");
        // verify
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"), word("xxxxx"));
    }

    @Test
    void submit5Guesses_Get5SubmittedGuesses() {
        WordleGame game = newGame("xxxxx", "yyyyy", "zzzzz");

        // exercise
        submit(game, "xxxxx", "yyyyy", "zzzzz", "xxxxx", "yyyyy");

        //verify
        assertThat(game.getSubmitted()).containsExactly(
                word("xxxxx"),
                word("yyyyy"),
                word("zzzzz"),
                word("xxxxx"),
                word("yyyyy")
        ).inOrder();
    }

    @Test
    void submitMoreThan5Guesses_Get5SubmittedGuessesAndFail() {
        WordleGame game = newGame("xxxxx", "yyyyy", "zzzzz", "aaaaa");
        String guess = "aaaaa";

        // exercise
        submit(game, "xxxxx", "yyyyy", "zzzzz", "xxxxx", "yyyyy");

        //verify
        assertThrows(IllegalStateException.class, () -> submit(game, guess));
        assertThrows(IllegalStateException.class, () -> submit(game, guess));
        assertThat(game.getSubmitted()).containsExactly(
                word("xxxxx"),
                word("yyyyy"),
                word("zzzzz"),
                word("xxxxx"),
                word("yyyyy")
        ).inOrder();
    }

    @Test
    void createNewGameWithDictionary_AcceptWord() {
        WordleGame game = newGame("xxxxx");
        // exercise
        assertDoesNotThrow(() -> submit(game, "xxxxx"));
        // verify
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"));
    }

    @Test
    void createNewGameWithDictionary_AcceptsOnlyWordsFromDictionary() {
        WordleGame game = newGame("aaaaa", "bbbbb", "ccccc", "ddddd");

        // exercise and verify
        assertDoesNotThrow(() -> submit(game, "aaaaa", "bbbbb"));
        assertThrows(IllegalArgumentException.class, () -> submit(game, "inval"));
        assertDoesNotThrow(() -> submit(game, "ccccc"));
        assertThrows(IllegalArgumentException.class, () -> submit(game, "xxxxx"));
        assertDoesNotThrow(() -> submit(game, "ccccc", "ddddd"));
        assertThrows(IllegalStateException.class, () -> submit(game, "xxxxx")); // more than 5 guesses

        assertThat(game.getSubmitted()).containsExactly(
                word("aaaaa"),
                word("bbbbb"),
                word("ccccc"),
                word("ccccc"),
                word("ddddd")
        ).inOrder();
    }

    private WordleGame newGame(String... dict) {
        return new WordleGame(Arrays.stream(dict).map(Word::new).collect(Collectors.toSet()));
    }

    private Word word(String word) {
        return new Word(word);
    }

    private void submit(WordleGame game, String... words) {
        for (String word : words) {
            game.submit(word(word));
        }
    }
}
