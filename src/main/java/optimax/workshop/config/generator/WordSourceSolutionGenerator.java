package optimax.workshop.config.generator;

import java.util.Random;
import optimax.workshop.core.Word;
import optimax.workshop.runner.SolutionGenerator;
import optimax.workshop.runner.WordSource;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordSourceSolutionGenerator implements SolutionGenerator {
    private final Word[] source;
    private final Random random = new Random();

    public WordSourceSolutionGenerator(WordSource source) {
        this.source = source.getAll().toArray(Word[]::new);
    }
    @Override
    public Word nextSolution() {
        return source[random.nextInt(source.length)];
    }
}
