package optimax.game;

import static com.google.common.truth.Truth.assertThat;
import static java.util.Map.entry;
import static optimax.game.TestUtilities.word;
import static optimax.game.matcher.Match.ABSENT;
import static optimax.game.matcher.Match.CORRECT;
import static optimax.game.matcher.Match.WRONG;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import optimax.game.accepter.DictionaryAccepter;
import optimax.game.matcher.Match;
import optimax.game.matcher.MatchResult;
import optimax.game.matcher.StandardMatcher;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordleGameTest {

    @Test
    void createNewGame() {
        assertDoesNotThrow(() -> new WordleGame(word("valid"), w -> true, new StandardMatcher()));
    }

    @Test
    void createNewGameWithNullArgumentsFails() {
        assertThrows(NullPointerException.class, () -> new WordleGame(null, w -> true, new StandardMatcher()));
        assertThrows(NullPointerException.class, () -> new WordleGame(word("valid"), null, new StandardMatcher()));
        assertThrows(NullPointerException.class, () -> new WordleGame(word("valid"), w -> true, null));

    }

    @Test
    void createNewGameWithNotAcceptedSolutionFails() {
        assertThrows(IllegalArgumentException.class, () -> new WordleGame(word("valid"), w -> false, new StandardMatcher()));
    }


    @Test
    void losingGame() {
        // setup
        Word illegal = word("xxxxx");
        Word solution = word("valid");
        Collection<Word> accepted = Set.of(solution, word("adult"), word("pesto"), word("aloha"), word("vvlid"), word("aaaaa"));
        WordleGame game = new WordleGame(solution, new DictionaryAccepter(accepted), new StandardMatcher());
        List<Map.Entry<Word, MatchResult>> guessesWithExpectedMatches = List.of(
                entry(word("adult"), matches(WRONG, WRONG, ABSENT, WRONG, ABSENT)),
                entry(word("pesto"), matches(ABSENT, ABSENT, ABSENT, ABSENT, ABSENT)),
                entry(word("aloha"), matches(WRONG, WRONG, ABSENT, ABSENT, ABSENT)),
                entry(word("vvlid"), matches(CORRECT, ABSENT, CORRECT, CORRECT, CORRECT)),
                entry(word("aaaaa"), matches(ABSENT, CORRECT, ABSENT, ABSENT, ABSENT)),
                entry(word("vvlid"), matches(CORRECT, ABSENT, CORRECT, CORRECT, CORRECT))
        );


        // exercise & verify
        for (Map.Entry<Word, MatchResult> guessAndMatch : guessesWithExpectedMatches) {
            assertFalse(game.isFinished());
            assertFalse(game.isSolved());
            assertThrows(IllegalArgumentException.class, () -> game.submit(illegal));

            assertEquals(guessAndMatch.getValue(), game.submit(guessAndMatch.getKey()), String.format("Matching %s with %s", guessAndMatch.getKey(), solution));
        }

        assertTrue(game.isFinished());
        assertFalse(game.isSolved());
        assertThrows(IllegalStateException.class, () -> game.submit(solution));

        assertThat(game.getSubmitted()).containsExactlyElementsIn(
                guessesWithExpectedMatches.stream().map(Map.Entry::getKey).collect(Collectors.toList())
        ).inOrder();

    }

    @Test
    void winningGame() {
        // setup
        Word illegal = word("xxxxx");
        Word solution = word("valid");
        Collection<Word> accepted = Set.of(solution, word("llall"), word("dilav"));
        WordleGame game = new WordleGame(solution, new DictionaryAccepter(accepted), new StandardMatcher());
        List<Map.Entry<Word, MatchResult>> guessesWithExpectedMatches = List.of(
                entry(word("llall"), matches(WRONG, ABSENT, WRONG, ABSENT, ABSENT)),
                entry(word("dilav"), matches(WRONG, WRONG, CORRECT, WRONG, WRONG)),
                entry(word("dilav"), matches(WRONG, WRONG, CORRECT, WRONG, WRONG)),
                entry(solution, matches(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT))
        );


        // exercise & verify
        for (Map.Entry<Word, MatchResult> guessAndMatch : guessesWithExpectedMatches) {
            assertFalse(game.isFinished());
            assertFalse(game.isSolved());
            assertThrows(IllegalArgumentException.class, () -> game.submit(illegal));

            assertEquals(guessAndMatch.getValue(), game.submit(guessAndMatch.getKey()), String.format("Matching %s with %s", guessAndMatch.getKey(), solution));
        }

        assertTrue(game.isFinished());
        assertTrue(game.isSolved());
        assertThrows(IllegalStateException.class, () -> game.submit(solution));

        assertThat(game.getSubmitted()).containsExactlyElementsIn(
                guessesWithExpectedMatches.stream().map(Map.Entry::getKey).collect(Collectors.toList())
        ).inOrder();

    }

    private MatchResult matches(Match... matches){
        return new MatchResult(matches);
    }
}
