package optimax.workshop.config.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import optimax.workshop.core.Word;
import optimax.workshop.wordsource.SolutionGenerator;

/**
 * A {@link SolutionGenerator} that generates next {@link Word} based
 * on the given collections. Each {@link Word} is randomly picked from the collection
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class CollectionSolutionGenerator implements SolutionGenerator {
    private final List<Word> source;
    private final Random random = new Random();

    public CollectionSolutionGenerator(Collection<Word> source) {
        this.source = new ArrayList<>(source);
    }

    @Override
    public Word nextSolution() {
        return source.get(random.nextInt(source.size()));
    }
}
