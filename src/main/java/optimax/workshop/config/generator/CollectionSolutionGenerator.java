package optimax.workshop.config.generator;

import java.util.Collection;
import java.util.Random;
import optimax.workshop.core.Word;
import optimax.workshop.runner.SolutionGenerator;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class CollectionSolutionGenerator implements SolutionGenerator {
    private final Word[] source;
    private final Random random = new Random();

    public CollectionSolutionGenerator(Collection<Word> source) {
        this.source = source.toArray(Word[]::new);
    }
    @Override
    public Word nextSolution() {
        return source[random.nextInt(source.length)];
    }
}
