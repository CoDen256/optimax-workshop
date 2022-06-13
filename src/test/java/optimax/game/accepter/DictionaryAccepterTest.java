package optimax.game.accepter;

import static optimax.game.TestUtilities.word;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class DictionaryAccepterTest {

    @Test
    void createEmptyDictionaryAccepter() {
       assertDoesNotThrow(() -> new DictionaryAccepter(Set.of()));
    }

    @Test
    void createDictionAryAccepterFailsOnNullSource() {
        assertThrows(NullPointerException.class, () -> new DictionaryAccepter(null));
    }

    @Test
    void createDictionaryAccepterAcceptsWordsGiven() {
        DictionaryAccepter sut = new DictionaryAccepter(List.of(word("xxxxx"), word("XXXXX"), word("Yyyyy"), word("valid")));

        assertTrue(sut.isAccepted(word("xxxxx")));
        assertTrue(sut.isAccepted(word("XxXxX")));
        assertTrue(sut.isAccepted(word("YyyYy")));
        assertTrue(sut.isAccepted(word("valid")));
        assertTrue(sut.isNotAccepted(word("valit")));
        assertTrue(sut.isNotAccepted(word("aaaaa")));
        assertTrue(sut.isNotAccepted(word("bbbbb")));
    }
}
