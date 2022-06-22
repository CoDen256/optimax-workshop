package optimax.workshop.core.match;

import static com.google.common.truth.Truth.assertThat;
import static java.util.Collections.emptyList;
import static optimax.workshop.core.match.MatchType.ABSENT;
import static optimax.workshop.core.match.MatchType.CORRECT;
import static optimax.workshop.core.match.MatchType.WRONG;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class MatchResultTest {
    @Test
    void createEmptyMatchResultFails() {
        assertThrows(MatchResult.InvalidMatchResultException.class, () -> new MatchResult(emptyList()));
    }

    @Test
    void createMatchResultWithNullMatches() {
        assertThrows(NullPointerException.class, () -> new MatchResult(null));
    }

    @Test
    void createMatchResultWithLessThan5MatchesFails() {
        assertThrows(MatchResult.InvalidMatchResultException.class, () -> result(
                m(0, 'a', ABSENT),
                m(0, 'a', CORRECT),
                m(0, 'a', WRONG),
                m(0, 'a', CORRECT)
        ));
    }

    @Test
    void createMatchResultWithMoreThan5MatchesFails() {
        assertThrows(MatchResult.InvalidMatchResultException.class, () -> result(
                m(0, 'a', ABSENT),
                m(0, 'a', CORRECT),
                m(0, 'a', WRONG),
                m(0, 'a', CORRECT),
                m(0, 'a', CORRECT),
                m(0, 'a', CORRECT)
        ));
    }


    @Test
    void createMatchResultWithMissingPositionFails() {
        assertThrows(MatchResult.MissingMatchPositionException.class, () -> result(
                m(0, 'a', ABSENT),
                m(1, 'a', CORRECT),
                m(2, 'a', WRONG),
                m(3, 'a', CORRECT),
                m(3, 'a', CORRECT)
        ));

        assertThrows(MatchResult.MissingMatchPositionException.class, () -> result(
                m(5, 'a', ABSENT),
                m(6, 'a', CORRECT),
                m(7, 'a', WRONG),
                m(8, 'a', CORRECT),
                m(9, 'a', CORRECT)
        ));
    }

    @Test
    void getMatchAtFailsIfNotInRange() {
        MatchResult result = result(
                m(0, 'a', ABSENT),
                m(1, 'a', CORRECT),
                m(2, 'a', WRONG),
                m(3, 'a', CORRECT),
                m(4, 'a', CORRECT)
        );
        assertThrows(IndexOutOfBoundsException.class, () -> result.matchTypeAt(-2));
        assertThrows(IndexOutOfBoundsException.class, () -> result.matchTypeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> result.matchTypeAt(5));
        assertThrows(IndexOutOfBoundsException.class, () -> result.matchTypeAt(6));
        assertThrows(IndexOutOfBoundsException.class, () -> result.matchTypeAt(12));
    }


    @Test
    void getMatchTypeAtPosition() {
        MatchResult result = result(
                m(0, 'a', ABSENT),
                m(1, 'a', CORRECT),
                m(2, 'a', WRONG),
                m(3, 'a', CORRECT),
                m(4, 'a', CORRECT)
        );
        assertEquals(ABSENT, result.matchTypeAt(0));
        assertEquals(CORRECT, result.matchTypeAt(1));
        assertEquals(WRONG, result.matchTypeAt(2));
        assertEquals(CORRECT, result.matchTypeAt(3));
        assertEquals(CORRECT, result.matchTypeAt(4));
    }

    @Test
    void getMatchAtPosition() {
        MatchResult result = result(
                m(0, 'a', ABSENT),
                m(1, 'a', CORRECT),
                m(2, 'a', WRONG),
                m(3, 'a', CORRECT),
                m(4, 'a', CORRECT)
        );
        assertEquals(m(0, 'a', ABSENT), result.matchAt(0));
        assertEquals(m(1, 'a', CORRECT), result.matchAt(1));
        assertEquals(m(2, 'a', WRONG), result.matchAt(2));
        assertEquals(m(3, 'a', CORRECT), result.matchAt(3));
        assertEquals(m(4, 'a', CORRECT), result.matchAt(4));
    }

    @Test
    void getMatchAtPositionIndependentOfOrder() {
        MatchResult result = result(
                m(4, 'a', CORRECT),
                m(2, 'a', WRONG),
                m(3, 'a', CORRECT),
                m(0, 'a', ABSENT),
                m(1, 'a', CORRECT)
                );
        assertEquals(m(0, 'a', ABSENT), result.matchAt(0));
        assertEquals(m(1, 'a', CORRECT), result.matchAt(1));
        assertEquals(m(2, 'a', WRONG), result.matchAt(2));
        assertEquals(m(3, 'a', CORRECT), result.matchAt(3));
        assertEquals(m(4, 'a', CORRECT), result.matchAt(4));
    }

    @Test
    void getMatches() {
        MatchResult result = result(
                m(0, 'a', ABSENT),
                m(1, 'b', CORRECT),
                m(2, 'c', WRONG),
                m(3, 'd', CORRECT),
                m(4, 'e', CORRECT)
        );

        assertThat(result.getMatches()).containsExactly(
                m( 0, 'a', ABSENT),
                m( 1, 'b', CORRECT),
                m( 2, 'c', WRONG),
                m( 3, 'd', CORRECT),
                m( 4, 'e', CORRECT)
        ).inOrder();
    }

    @Test
    void getMatchesIndependentOfOrder() {
        MatchResult result = result(
                m(4, 'e', CORRECT),
                m(1, 'b', CORRECT),
                m(2, 'c', WRONG),
                m(3, 'd', CORRECT),
                m(0, 'a', ABSENT)
                );

        assertThat(result.getMatches()).containsExactly(
                m( 0, 'a', ABSENT),
                m( 1, 'b', CORRECT),
                m( 2, 'c', WRONG),
                m( 3, 'd', CORRECT),
                m( 4, 'e', CORRECT)
        ).inOrder();
    }

    @Test
    void differentResultsAreNotEqual() {
        MatchResult result = result(
                m(0, 'a', ABSENT),
                m(1, 'b', CORRECT),
                m(2, 'c', WRONG),
                m(3, 'd', CORRECT),
                m(4, 'e', CORRECT)
        );
        MatchResult result2 = result(
                m(0, 'a', ABSENT),
                m(1, 'b', CORRECT),
                m(2, 'c', WRONG),
                m(3, 'd', CORRECT),
                m(4, 'e', ABSENT)
        );

        assertNotEquals(result, result2);
    }

    @Test
    void sameResultsAreEqual() {
        MatchResult result = result(
                m(0, 'a', ABSENT),
                m(1, 'b', CORRECT),
                m(2, 'c', WRONG),
                m(3, 'd', CORRECT),
                m(4, 'e', CORRECT)
        );
        MatchResult result2 = result(
                m(0, 'a', ABSENT),
                m(1, 'b', CORRECT),
                m(2, 'c', WRONG),
                m(3, 'd', CORRECT),
                m(4, 'e', CORRECT)
        );

        assertEquals(result, result2);
    }

    @Test
    void sameResultsDifferentOrderAreEqual() {
        MatchResult result = result(
                m(0, 'a', ABSENT),
                m(1, 'b', CORRECT),
                m(2, 'c', WRONG),
                m(3, 'd', CORRECT),
                m(4, 'e', CORRECT)
        );
        MatchResult result2 = result(
                m(2, 'c', WRONG),
                m(3, 'd', CORRECT),
                m(0, 'a', ABSENT),
                m(4, 'e', CORRECT),
                m(1, 'b', CORRECT)
                );

        assertEquals(result, result2);
    }

    @Test
    void matchResultToString() {
        MatchResult result = result(
                m(0, 'a', ABSENT),
                m(1, 'b', CORRECT),
                m(2, 'c', WRONG),
                m(3, 'd', CORRECT),
                m(4, 'e', ABSENT)
        );
        assertEquals("[x+-+x]", result.toString());
    }

    @Test
    void sameMatchesAreEqual() {
        assertEquals(m(0, 'a', ABSENT), m(0, 'a', ABSENT));
        assertEquals(m(0, 'b', ABSENT), m(0, 'b', ABSENT));
        assertEquals(m(1, 'b', ABSENT), m(1, 'b', ABSENT));
        assertEquals(m(1, 'b', CORRECT), m(1, 'b', CORRECT));
    }

    @Test
    void differentMatchesAreNotEqual() {
        assertNotEquals(m(0, 'a', ABSENT), m(0, 'b', ABSENT));
        assertNotEquals(m(0, 'a', ABSENT), m(1, 'a', ABSENT));
        assertNotEquals(m(0, 'a', ABSENT), m(0, 'a', CORRECT));
        assertNotEquals(m(0, 'a', ABSENT), m(1, 'b', CORRECT));
    }

    private Match m(int position, char letter, MatchType type) {
        return new Match(type, position, letter);
    }

    private MatchResult result(Match... matches) {
        return new MatchResult(Arrays.asList(matches));
    }
}