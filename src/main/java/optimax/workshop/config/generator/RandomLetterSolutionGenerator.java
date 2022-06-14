package optimax.workshop.config.generator;

import optimax.workshop.core.Word;
import optimax.workshop.core.dictionary.SolutionGenerator;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RandomLetterSolutionGenerator implements SolutionGenerator {
    @Override
    public Word nextSolution() {
        return new Word("nulls");
    }
}
