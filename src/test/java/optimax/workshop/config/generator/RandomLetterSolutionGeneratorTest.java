package optimax.workshop.config.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import optimax.workshop.core.Word;
import org.junit.jupiter.api.RepeatedTest;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class RandomLetterSolutionGeneratorTest {

    @RepeatedTest(50)
    void randomWord() {
        RandomLetterSolutionGenerator generator = new RandomLetterSolutionGenerator();

        Word word = generator.nextSolution();
        assertNotNull(word);
        assertEquals(5, word.word().length());
    }
}