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
}
