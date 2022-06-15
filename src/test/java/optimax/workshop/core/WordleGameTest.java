package optimax.workshop.core;

import static com.google.common.truth.Truth.assertThat;
import static java.util.Map.entry;
import static optimax.workshop.TestUtilities.word;
import static optimax.workshop.core.matcher.MatchType.ABSENT;
import static optimax.workshop.core.matcher.MatchType.CORRECT;
import static optimax.workshop.core.matcher.MatchType.WRONG;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import optimax.workshop.config.matcher.StandardMatcher;
import optimax.workshop.core.matcher.Match;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.core.matcher.MatchType;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleGameTest {

    @Test
    void createNewGame() {
        assertDoesNotThrow(() -> new WordleGame(1, word("valid"), new StandardMatcher()));
    }

    @Test
    void createNewGameWithNullArgumentsFails() {
        assertThrows(NullPointerException.class, () -> new WordleGame(6, null, new StandardMatcher()));
        assertThrows(NullPointerException.class, () -> new WordleGame(6, word("valid"), null));

    }

    @Test
    void createNewGameWithZeroOrNegativeAttempts() {
        assertThrows(IllegalArgumentException.class, () -> new WordleGame(-1, word("valid"), new StandardMatcher()));
        assertThrows(IllegalArgumentException.class, () -> new WordleGame(0, word("valid"), new StandardMatcher()));
        assertThrows(IllegalArgumentException.class, () -> new WordleGame(-20, word("valid"), new StandardMatcher()));
    }


    @Test
    void losingGame() {
        // setup
//        Word solution = word("valid");
//        WordleGame game = new WordleGame(6, solution, new StandardMatcher());
//        List<Map.Entry<Word, MatchResult>> guessesWithExpectedMatches = List.of(
//                entry(word("adult"), matches(WRONG, WRONG, ABSENT, WRONG, ABSENT)),
//                entry(word("pesto"), matches(ABSENT, ABSENT, ABSENT, ABSENT, ABSENT)),
//                entry(word("aloha"), matches(WRONG, WRONG, ABSENT, ABSENT, ABSENT)),
//                entry(word("vvlid"), matches(CORRECT, ABSENT, CORRECT, CORRECT, CORRECT)),
//                entry(word("aaaaa"), matches(ABSENT, CORRECT, ABSENT, ABSENT, ABSENT)),
//                entry(word("vvlid"), matches(CORRECT, ABSENT, CORRECT, CORRECT, CORRECT))
//        );
//
//
//        // exercise & verify
//        for (Map.Entry<Word, MatchResult> guessAndMatch : guessesWithExpectedMatches) {
//            assertFalse(game.isFinished());
//            assertFalse(game.isSolved());
//
//            assertEquals(guessAndMatch.getValue(), game.submit(guessAndMatch.getKey()), String.format("Matching %s with %s", guessAndMatch.getKey(), solution));
//        }
//
//        assertTrue(game.isFinished());
//        assertFalse(game.isSolved());
//        assertThrows(IllegalStateException.class, () -> game.submit(solution));
//
//        assertThat(game.getSubmitted()).containsExactlyElementsIn(
//                guessesWithExpectedMatches.stream().map(Map.Entry::getKey).collect(Collectors.toList())
//        ).inOrder();

    }

    @Test
    void winningGame() {
        // setup
//        Word solution = word("valid");
//        WordleGame game = new WordleGame(6, solution, new StandardMatcher());
//        List<Map.Entry<Word, MatchResult>> guessesWithExpectedMatches = List.of(
//                entry(word("llall"), matches(WRONG, ABSENT, WRONG, ABSENT, ABSENT)),
//                entry(word("dilav"), matches(WRONG, WRONG, CORRECT, WRONG, WRONG)),
//                entry(word("dilav"), matches(WRONG, WRONG, CORRECT, WRONG, WRONG)),
//                entry(solution, matches(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT))
//        );
//
//
//        // exercise & verify
//        for (Map.Entry<Word, MatchResult> guessAndMatch : guessesWithExpectedMatches) {
//            assertFalse(game.isFinished());
//            assertFalse(game.isSolved());
//
//            assertEquals(guessAndMatch.getValue(), game.submit(guessAndMatch.getKey()), String.format("Matching %s with %s", guessAndMatch.getKey(), solution));
//        }
//
//        assertTrue(game.isFinished());
//        assertTrue(game.isSolved());
//        assertThrows(IllegalStateException.class, () -> game.submit(solution));
//
//        assertThat(game.getSubmitted()).containsExactlyElementsIn(
//                guessesWithExpectedMatches.stream().map(Map.Entry::getKey).collect(Collectors.toList())
//        ).inOrder();

    }

    private MatchResult matches(MatchType t0, char c0,
                                MatchType t1, char c1,
                                MatchType t2, char c2,
                                MatchType t3, char c3,
                                MatchType t4, char c4){
        return new MatchResult(
                List.of(
                        new Match(t0, 0, c0),
                        new Match(t1, 1, c1),
                        new Match(t2, 2, c2),
                        new Match(t3, 3, c3),
                        new Match(t4, 4, c4)
                )
        );
    }
}
