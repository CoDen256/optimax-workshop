package optimax.game;

import static optimax.game.Match.ABSENT;
import static optimax.game.Match.CORRECT;
import static optimax.game.Match.WRONG;
import static optimax.game.WordleGameTestUtils.word;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleGameMatchingTest {


    @Test
    void sameWordReturnsAllCorrect() {
        WordleGame game = new WordleGame(word("abcde"), s -> true);
        assertMatches("+++++", game.submit(word("abcde")));
    }

    @Test
    void absentLettersReturnAllAbsent() {
        WordleGame game = new WordleGame(word("abcde"), s -> true);
        assertMatches("xxxxx", game.submit(word("fghjk")));
    }

//    @Test
//    void submittedWordReturnsMatchResult() {
//        Word solution = word("valid");
//        WordleGame game = new WordleGame(solution, s -> true);
//
//        assertEquals(matchResult(WRONG, CORRECT, CORRECT, CORRECT, CORRECT), game.submit(word("aalid")));
//        assertEquals(matchResult(ABSENT, ABSENT, ABSENT, ABSENT, ABSENT), game.submit(word("zzzzz")));
//        assertEquals(matchResult(WRONG, CORRECT, WRONG, WRONG, WRONG), game.submit(word("aaaaa")));
//        assertEquals(matchResult(CORRECT, CORRECT, WRONG, WRONG, ABSENT), game.submit(word("vaAVk")));
//        assertEquals(matchResult(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT), game.submit(solution));
//    }

    private void assertMatches(String expected, MatchResult result) {
        assertEquals(String.format("Result[%s]", expected), result.toString());
    }
}
