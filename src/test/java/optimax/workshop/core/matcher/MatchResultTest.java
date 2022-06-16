package optimax.workshop.core.matcher;

import static com.google.common.truth.Truth.assertThat;
import static java.util.Collections.emptyList;
import static optimax.workshop.core.matcher.MatchType.ABSENT;
import static optimax.workshop.core.matcher.MatchType.CORRECT;
import static optimax.workshop.core.matcher.MatchType.WRONG;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class MatchResultTest {
    @Test
    void createEmptyMatchResultFails() {
        assertThrows(IllegalArgumentException.class, () -> new MatchResult(emptyList()));
    }

    @Test
    void createMatchResultWithNullMatches() {
        assertThrows(NullPointerException.class, () -> new MatchResult( null));
    }

    @Test
    void createMatchResultWithLessThan5MatchesFails() {
        assertThrows(IllegalArgumentException.class, () -> matchResult(
                ABSENT, CORRECT, WRONG, CORRECT
        ));
    }

    @Test
    void createMatchResultWithMoreThan5MatchesFails() {
        assertThrows(IllegalArgumentException.class, () -> matchResult(
                ABSENT, CORRECT, WRONG, CORRECT, CORRECT, WRONG
        ));
    }


    @Test
    void createMatchResultWith5Matches() {
        assertDoesNotThrow(() -> matchResult(
                ABSENT, CORRECT, WRONG, CORRECT, CORRECT
        ));
    }

    @Test
    void getMatchAtFailsIfNotInRange() {
        MatchResult result = matchResult(
                ABSENT, CORRECT, WRONG, CORRECT, CORRECT
        );
        assertThrows(IllegalArgumentException.class, () -> result.matchTypeAt(-2));
        assertThrows(IllegalArgumentException.class, () -> result.matchTypeAt(-1));
        assertThrows(IllegalArgumentException.class, () -> result.matchTypeAt(5));
        assertThrows(IllegalArgumentException.class, () -> result.matchTypeAt(6));
        assertThrows(IllegalArgumentException.class, () -> result.matchTypeAt(12));
    }


    @Test
    void getMatchAtPosition() {
        MatchResult result = matchResult(
                ABSENT, CORRECT, WRONG, CORRECT, CORRECT
        );

        assertEquals(ABSENT, result.matchTypeAt(0));
        assertEquals(CORRECT, result.matchTypeAt(1));
        assertEquals(WRONG, result.matchTypeAt(2));
        assertEquals(CORRECT, result.matchTypeAt(3));
        assertEquals(CORRECT, result.matchTypeAt(4));
    }

    @Test
    void getMatches(){
        MatchResult result = matchResult(
                ABSENT, CORRECT, WRONG, CORRECT, CORRECT
        );

        assertThat(result.getMatches()).containsExactly(
                new Match(ABSENT, 0, 'a'),
                new Match(CORRECT, 1, 'a'),
                new Match(WRONG, 2, 'a'),
                new Match(CORRECT, 3, 'a'),
                new Match(CORRECT, 4, 'a')
        );
    }

    @Test
    void differentResultsAreNotEqual() {
        MatchResult result = matchResult(
                ABSENT, CORRECT, WRONG, CORRECT, CORRECT
        );
        MatchResult result2 = matchResult(
                ABSENT, WRONG, WRONG, WRONG, WRONG
        );

        assertNotEquals(result, result2);
    }

    @Test
    void sameResultsAreEqual() {
        MatchResult result = matchResult(
                ABSENT, CORRECT, WRONG, CORRECT, CORRECT
        );
        MatchResult result2 = matchResult(
                ABSENT, CORRECT, WRONG, CORRECT, CORRECT
        );

        assertEquals(result, result2);
    }

    @Test
    void sameResultsDifferentOrderAreNotEqual() {
        MatchResult result = matchResult(
                ABSENT, CORRECT, WRONG, CORRECT, CORRECT
        );
        MatchResult result2 = matchResult(
                ABSENT, CORRECT, CORRECT, WRONG, CORRECT
        );

        assertNotEquals(result, result2);
    }

    @Test
    void matchResultToString() {
        MatchResult result = matchResult(ABSENT, CORRECT, WRONG, CORRECT, ABSENT);
        assertEquals("[x+-+x]", result.toString());
    }

    private MatchResult matchResult(MatchType... types){
        return new MatchResult(IntStream
                .range(0, types.length)
                .mapToObj(t -> new Match(types[t], t, 'a'))
                .collect(Collectors.toList()));
    }
}