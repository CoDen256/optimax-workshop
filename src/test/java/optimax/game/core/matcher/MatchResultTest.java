package optimax.game.core.matcher;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class MatchResultTest {
    @Test
    void createEmptyMatchResultFails() {
        assertThrows(IllegalArgumentException.class, () -> new MatchResult(List.of()));
    }

    @Test
    void createMatchResultWithNullMatches() {
        assertThrows(NullPointerException.class, () -> new MatchResult((List<Match>) null));
    }

    @Test
    void createMatchResultWithLessThan5MatchesFails() {
        assertThrows(IllegalArgumentException.class, () -> new MatchResult(
                List.of(Match.ABSENT, Match.CORRECT, Match.WRONG, Match.CORRECT)
        ));
    }

    @Test
    void createMatchResultWithMoreThan5MatchesFails() {
        assertThrows(IllegalArgumentException.class, () -> new MatchResult(
                List.of(Match.ABSENT, Match.CORRECT, Match.WRONG, Match.CORRECT, Match.CORRECT, Match.WRONG)
        ));
    }


    @Test
    void createMatchResultWith5Matches() {
        assertDoesNotThrow(() -> new MatchResult(
                List.of(Match.ABSENT, Match.CORRECT, Match.WRONG, Match.CORRECT, Match.CORRECT)
        ));
    }

    @Test
    void getMatchAtFailsIfNotInRange() {
        MatchResult result = new MatchResult(
                List.of(Match.ABSENT, Match.CORRECT, Match.WRONG, Match.CORRECT, Match.CORRECT)
        );
        assertThrows(IllegalArgumentException.class, () -> result.getMatch(-2));
        assertThrows(IllegalArgumentException.class, () -> result.getMatch(-1));
        assertThrows(IllegalArgumentException.class, () -> result.getMatch(5));
        assertThrows(IllegalArgumentException.class, () -> result.getMatch(6));
        assertThrows(IllegalArgumentException.class, () -> result.getMatch(12));
    }


    @Test
    void getMatchAtPosition() {
        MatchResult result = new MatchResult(
                List.of(Match.ABSENT, Match.CORRECT, Match.WRONG, Match.CORRECT, Match.CORRECT)
        );

        assertEquals(Match.ABSENT, result.getMatch(0));
        assertEquals(Match.CORRECT, result.getMatch(1));
        assertEquals(Match.WRONG, result.getMatch(2));
        assertEquals(Match.CORRECT, result.getMatch(3));
        assertEquals(Match.CORRECT, result.getMatch(4));
    }

    @Test
    void differentResultsAreNotEqual() {
        MatchResult result = new MatchResult(
                List.of(Match.ABSENT, Match.CORRECT, Match.WRONG, Match.CORRECT, Match.CORRECT)
        );
        MatchResult result2 = new MatchResult(
                List.of(Match.ABSENT, Match.WRONG, Match.WRONG, Match.WRONG, Match.WRONG)
        );

        assertNotEquals(result, result2);
    }

    @Test
    void sameResultsAreEqual() {
        MatchResult result = new MatchResult(
                List.of(Match.ABSENT, Match.CORRECT, Match.WRONG, Match.CORRECT, Match.CORRECT)
        );
        MatchResult result2 = new MatchResult(
                List.of(Match.ABSENT, Match.CORRECT, Match.WRONG, Match.CORRECT, Match.CORRECT)
        );

        assertEquals(result, result2);
    }

    @Test
    void sameResultsDifferentOrderAreNotEqual() {
        MatchResult result = new MatchResult(
                List.of(Match.ABSENT, Match.CORRECT, Match.WRONG, Match.CORRECT, Match.CORRECT)
        );
        MatchResult result2 = new MatchResult(
                List.of(Match.ABSENT, Match.CORRECT, Match.CORRECT, Match.WRONG, Match.CORRECT)
        );

        assertNotEquals(result, result2);
    }

    @Test
    void matchResultToString() {
        MatchResult result = new MatchResult(List.of(Match.ABSENT, Match.CORRECT, Match.WRONG, Match.CORRECT, Match.ABSENT));
        assertEquals("Result[x+-+x]", result.toString());
    }
}