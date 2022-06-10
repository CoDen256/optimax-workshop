package optimax.game;

import static com.google.common.truth.Truth.assertThat;
import static optimax.game.TestUtilities.word;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import optimax.game.matcher.StandardMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleGameSubmittingTest {

    private final static Word SOLUTION = word("valid");

    private WordleGame game;

    @BeforeEach
    void setUp() {
        game = new WordleGame(SOLUTION, w -> true, new StandardMatcher());
    }

    @Test
    void emptyGameIsNotFinished() {
        assertFalse(game.isFinished());
    }

    @Test
    void emptyGameIsNotSolved() {
        assertFalse(game.isSolved());
    }

    @Test
    void inEmptyGameSubmittedGuessesAreEmpty() {
        assertThat(game.getSubmitted()).isEmpty();
    }


    @Test
    void submitNullFails() {
        assertThrows(NullPointerException.class, () -> game.submit(null));
    }

    @Test
    void submitAfterSolutionFails() {
        // exercise
        submit(word("xxxxx"), word("aaaaa"));
        submit(SOLUTION);
        // verify
        assertThrows(IllegalStateException.class, () -> submit(word("xxxxx")));
        assertThrows(IllegalStateException.class, () -> submit(SOLUTION));
        assertThrows(IllegalStateException.class, () -> submit(word("aaaaa")));
    }

    @Test
    void gameIsFinishedAndSolvedAfterSolutionSubmitted() {
        submit(word("xxxxx"), word("aaaaa"), word("xxxxx"));
        assertFalse(game.isSolved());
        assertFalse(game.isFinished());

        submit(SOLUTION);
        assertTrue(game.isSolved());
        assertTrue(game.isFinished());
    }

    @Test
    void submitWord5Times_GameIsFinished() {
        submit(word("xxxxx"), word("yyyyy"), word("zzzzz"), word("xxxxx"));
        assertFalse(game.isFinished());
        submit(word("yyyyy"));
        assertTrue(game.isFinished());
    }

    @Test
    void submitWordMoreThan5TimesFails() {
        Word guess = word("xxxxx");
        // exercise
        for (int i = 0; i < 5; i++) {
            submit(guess);
        }
        // verify
        assertThrows(IllegalStateException.class, () -> submit(guess));
        assertThrows(IllegalStateException.class, () -> submit(guess));
    }

    @Test
    void gameIsNotSolvedIfNoSolutionInserted() {
        submit(word("xxxxx"), word("aaaaa"), word("xxxxx"));
        assertFalse(game.isSolved());
        assertFalse(game.isFinished());

        submit(word("xxxxx"), word("zzzzz"));
        assertFalse(game.isSolved());
        assertTrue(game.isFinished());

        assertThrows(IllegalStateException.class, () -> submit(SOLUTION));
        assertThrows(IllegalStateException.class, () -> submit(word("xxxxx")));
        assertFalse(game.isSolved());
        assertTrue(game.isFinished());
    }


    @Test
    void submitGuess_GetSubmittedGuess() {
        submit(word("xxxxx"));
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"));
    }

    @Test
    void submit2Guesses_Get2SubmittedGuesses() {
        submit(word("xxxxx"), word("yyyyy"));
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"), word("yyyyy")).inOrder();
    }

    @Test
    void submit3Guesses_Get3SubmittedGuesses() {
        submit(word("xxxxx"), word("yyyyy"), word("zzzzz"));
        assertThat(game.getSubmitted()).containsExactly(
                word("xxxxx"),
                word("yyyyy"),
                word("zzzzz")
        ).inOrder();
    }

    @Test
    void submit2SameGuesses_Get2SameSubmittedGuesses() {
        submit(word("xxxxx"), word("xxxxx"));
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"), word("xxxxx"));
    }

    @Test
    void submit5Guesses_Get5SubmittedGuesses() {
        submit(word("xxxxx"), word("yyyyy"), word("zzzzz"), word("xxxxx"), word("yyyyy"));
        assertThat(game.getSubmitted()).containsExactly(
                word("xxxxx"),
                word("yyyyy"),
                word("zzzzz"),
                word("xxxxx"),
                word("yyyyy")
        ).inOrder();
    }

    @Test
    void submitIntegration_noSolution() {
        // No guesses
        assertFalse(game.isFinished());
        assertFalse(game.isSolved());

        // First guess
        submit(word("xxxxx"));
        assertFalse(game.isFinished());
        assertFalse(game.isSolved());

        // Second guess
        submit(word("yyyyy"));
        assertFalse(game.isFinished());
        assertFalse(game.isSolved());

        // Third guess
        submit(word("zzzzz"));
        assertFalse(game.isFinished());
        assertFalse(game.isSolved());

        // Fourth guess
        submit(word("xxxxx"));
        assertFalse(game.isFinished());
        assertFalse(game.isSolved());

        // Fifth guess
        submit(word("yyyyy"));
        assertTrue(game.isFinished());
        assertFalse(game.isSolved());

        // Post-finished game
        assertThrows(IllegalStateException.class, () -> submit(word("xxxxx")));
        assertThrows(IllegalStateException.class, () -> submit(word("yyyyy")));
        assertThrows(IllegalStateException.class, () -> submit(SOLUTION));
        assertTrue(game.isFinished());
        assertFalse(game.isSolved());

        assertThat(game.getSubmitted()).containsExactly(
                word("xxxxx"),
                word("yyyyy"),
                word("zzzzz"),
                word("xxxxx"),
                word("yyyyy")
        ).inOrder();
    }

    @Test
    void submitIntegration_withSolution() {
        // No guesses
        assertFalse(game.isFinished());
        assertFalse(game.isSolved());

        // First guess
        submit(word("xxxxx"));
        assertFalse(game.isFinished());
        assertFalse(game.isSolved());

        // Second guess
        submit(word("yyyyy"));
        assertFalse(game.isFinished());
        assertFalse(game.isSolved());

        // Third guess
        submit(SOLUTION);
        assertTrue(game.isFinished());
        assertTrue(game.isSolved());

        // Post-finished game
        assertThrows(IllegalStateException.class, () -> submit(word("xxxxx")));
        assertThrows(IllegalStateException.class, () -> submit(word("yyyyy")));
        assertThrows(IllegalStateException.class, () -> submit(SOLUTION));
        assertTrue(game.isFinished());
        assertTrue(game.isSolved());

        assertThat(game.getSubmitted()).containsExactly(
                word("xxxxx"),
                word("yyyyy"),
                SOLUTION
        ).inOrder();
    }

    private void submit(Word... words) {
        for (Word word : words) {
            game.submit(word);
        }
    }

}
