package optimax;

import static optimax.Match.ABSENT;
import static optimax.Match.CORRECT;
import static optimax.Match.PRESENT;
import static optimax.TestUtils.word;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleGameMatchingTest {

//    @Test
    void submittedWordReturnsMatchResult() {
        Word solution = word("valid");
        WordleGame game = new WordleGame(solution, s -> true);

        assertEquals(matchResult(PRESENT, CORRECT, CORRECT, CORRECT, CORRECT), game.submit(word("aalid")));
        assertEquals(matchResult(ABSENT, ABSENT, ABSENT, ABSENT, ABSENT), game.submit(word("zzzzz")));
        assertEquals(matchResult(PRESENT, CORRECT, PRESENT, PRESENT, PRESENT), game.submit(word("aaaaa")));
        assertEquals(matchResult(CORRECT, CORRECT, PRESENT, PRESENT, ABSENT), game.submit(word("vaAVk")));
        assertEquals(matchResult(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT), game.submit(solution));
    }

    private MatchResult matchResult(Match... matches) {
        return new MatchResult(Arrays.asList(matches));
    }

}
