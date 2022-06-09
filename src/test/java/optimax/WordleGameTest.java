package optimax;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleGameTest {

    @Test
    void createNewGame_GameIsNotFinished() {
        WordleGame game = new WordleGame(word("valid"), Set.of(word("valid")));
        assertFalse(game.isFinished());
    }

    @Test
    void createNewGame_GameIsNotSolved() {
        WordleGame game = new WordleGame(word("valid"), Set.of(word("valid")));
        assertFalse(game.isSolved());
    }

    @Test
    void submitNullFails() {
        WordleGame game = new WordleGame(word("valid"), Set.of(word("valid")));
        assertThrows(NullPointerException.class, () -> game.submit(null));
    }

    @Test
    void submitWord5Times_GameIsFinished() {
        WordleGame game = newFakeGame("xxxxx", "yyyyy", "zzzzz");
        submit(game, "xxxxx", "yyyyy", "zzzzz", "xxxxx");
        assertFalse(game.isFinished());
        submit(game, "yyyyy");
        assertTrue(game.isFinished());
    }

    @Test
    void submitWordMoreThan5TimesFails() {
        WordleGame game = newFakeGame("xxxxx");
        String guess = "xxxxx";
        // exercise
        submit(game, guess, guess, guess, guess, guess);
        // verify
        assertThrows(IllegalStateException.class, () -> submit(game, guess));
        assertThrows(IllegalStateException.class, () -> submit(game, guess));
    }

    @Test
    void createNewGame_GetEmptySubmittedGuesses() {
        WordleGame game = newFakeGame();
        assertThat(game.getSubmitted()).isEmpty();
    }

    @Test
    void submitGuess_GetSubmittedGuess() {
        WordleGame game = newFakeGame("xxxxx");
        submit(game, "xxxxx");
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"));
    }

    @Test
    void submit2Guesses_Get2SubmittedGuesses() {
        WordleGame game = newFakeGame("xxxxx", "yyyyy");
        // exercise
        submit(game, "xxxxx", "yyyyy");
        // verify
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"), word("yyyyy")).inOrder();
    }

    @Test
    void submit3Guesses_Get3SubmittedGuesses() {
        WordleGame game = newFakeGame("xxxxx", "yyyyy", "zzzzz");
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
        WordleGame game = newFakeGame("xxxxx");
        // exercise
        submit(game, "xxxxx", "xxxxx");
        // verify
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"), word("xxxxx"));
    }

    @Test
    void submit5Guesses_Get5SubmittedGuesses() {
        WordleGame game = newFakeGame("xxxxx", "yyyyy", "zzzzz");

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
        WordleGame game = newFakeGame("xxxxx", "yyyyy", "zzzzz", "aaaaa");
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
    void createNewGameWithNullDictionaryFails() {
        assertThrows(NullPointerException.class, () -> new WordleGame(word("valid"), null));
    }

    @Test
    void createNewGameWithDictionary_AcceptWord() {
        WordleGame game = newFakeGame("xxxxx");
        // exercise
        assertDoesNotThrow(() -> submit(game, "xxxxx"));
        // verify
        assertThat(game.getSubmitted()).containsExactly(word("xxxxx"));
    }

    @Test
    void createNewGameWithDictionary_RejectsWordsThatAreNotInDictionary() {
        WordleGame game = newFakeGame("xxxxx");
        // exercise
        assertThrows(IllegalArgumentException.class, () -> submit(game, "yyyyy"));
        assertThrows(IllegalArgumentException.class, () -> submit(game, "zzzzz"));
        assertThrows(IllegalArgumentException.class, () -> submit(game, "aaaaa"));
        // verify
        assertThat(game.getSubmitted()).isEmpty();
    }

    @Test
    void createNewGameWithDictionary_AcceptsOnlyWordsFromDictionary() {
        WordleGame game = newFakeGame("aaaaa", "bbbbb", "ccccc", "ddddd");

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

    @Test
    void createNewGameWithDictionaryCaseInsensitive() {
        WordleGame game = newFakeGame("Aaaaa", "BBBBB", "CcCcC", "dddDD");

        // exercise and verify
        assertDoesNotThrow(() -> submit(game, "aaaaA", "bbbbb", "cCcCc", "dDDdd"));

        assertThat(game.getSubmitted()).containsExactly(
                word("aaaaa"),
                word("bbbbb"),
                word("ccccc"),
                word("ddddd")
        ).inOrder();
    }

    @Test
    void createGameWithTarget() {
        assertDoesNotThrow(() -> newGame("targt", "targt"));
        assertDoesNotThrow(() -> newGame("targt", "abcde", "TARGT"));
        assertDoesNotThrow(() -> newGame("TARGT", "aaaaa", "bbbbb", "targT"));
    }

    @Test
    void createGameWithTargetThatIsNotInDictFails() {
        assertThrows(IllegalArgumentException.class, () -> newGame("targt"));
        assertThrows(IllegalArgumentException.class, () -> newGame("targt", "trget", "xxxxx"));
        assertThrows(IllegalArgumentException.class, () -> newGame("targt", "aaaaa", "abcde"));
    }

    @Test
    void createGameWithNullTargetFails() {
        assertThrows(NullPointerException.class, () -> new WordleGame(null, Set.of(word("valid"))));
    }

    @Test
    void submitSomethingAfterTargetFails() {
        WordleGame game = newGame("valid", "valid", "xxxxx", "aaaaa");
        // exercise
        submit(game, "xxxxx", "aaaaa");
        submit(game, "valid");
        // verify
        assertThrows(IllegalStateException.class, () -> submit(game, "xxxxx"));
        assertThrows(IllegalStateException.class, () -> submit(game, "valid"));
        assertThrows(IllegalStateException.class, () -> submit(game, "aaaaa"));
    }

    @Test
    void gameIsFinishedAndSolvedAfterTargetSubmitted() {
        WordleGame game = newGame("valid", "valid", "xxxxx", "aaaaa", "zzzzz");

        submit(game, "xxxxx", "aaaaa", "xxxxx");
        assertFalse(game.isSolved());
        assertFalse(game.isFinished());

        submit(game, "valid");
        assertTrue(game.isSolved());
        assertTrue(game.isFinished());

        assertThrows(IllegalStateException.class, () -> submit(game, "valid"));

        assertThat(game.getSubmitted()).containsExactly(
                word("xxxxx"),
                word("aaaaa"),
                word("xxxxx"),
                word("valid")
        ).inOrder();
    }

    @Test
    void gameIsNotSolvedIfNoTargetInserted() {
        WordleGame game = newGame("valid", "valid", "xxxxx", "aaaaa", "zzzzz");

        submit(game, "xxxxx", "aaaaa", "xxxxx");
        assertFalse(game.isSolved());
        assertFalse(game.isFinished());
        submit(game, "xxxxx", "zzzzz");
        assertFalse(game.isSolved());
        assertTrue(game.isFinished());

        assertThrows(IllegalStateException.class, () -> submit(game, "valid"));

        assertThat(game.getSubmitted()).containsExactly(
                word("xxxxx"),
                word("aaaaa"),
                word("xxxxx"),
                word("xxxxx"),
                word("zzzzz")
        ).inOrder();
    }

    private WordleGame newFakeGame(String... dict) {
        Collection<String> resultDict = new ArrayList<>(Arrays.asList(dict));
        String fakeTarget = "valid";
        resultDict.add(fakeTarget);
        return newGame(fakeTarget, resultDict);
    }

    private WordleGame newGame(String solution, String... dict) {
        return new WordleGame(word(solution), Arrays.stream(dict).map(Word::new).collect(Collectors.toSet()));
    }

    private WordleGame newGame(String solution, Collection<String> dict) {
        return new WordleGame(word(solution), dict.stream().map(Word::new).collect(Collectors.toSet()));
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