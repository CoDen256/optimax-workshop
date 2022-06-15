package optimax.workshop.config.generator;

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
class WordSourceSolutionGeneratorTest {

    @Test
    void generateNextSolution() {
        Collection<Word> source = generateWords(50).collect(Collectors.toList());
        WordSourceSolutionGenerator sut = new WordSourceSolutionGenerator(() -> source);

        for (int i = 0; i < 50; i++) {
            Word word = sut.nextSolution();
            assertTrue(source.contains(word));
        }
    }
}