package optimax.workshop.config.accepter;

import static optimax.workshop.TestUtilities.generateWords;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.stream.Collectors;
import optimax.workshop.core.Word;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordSourceAccepterTest {
    @Test
    void acceptFromWordSource() {
        Collection<Word> source = generateWords(50).collect(Collectors.toList());
        WordSourceAccepter sut = new WordSourceAccepter(() -> source);

        for (Word word : source) {
            assertTrue(sut.isAccepted(word));
        }
    }
}