package optimax.workshop.config.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import optimax.workshop.core.Word;
import optimax.workshop.core.dictionary.SolutionGenerator;
import optimax.workshop.core.dictionary.WordSource;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordSourceSolutionGenerator implements SolutionGenerator {

    private final WordSource source;

    public WordSourceSolutionGenerator(WordSource source) {
        this.source = source;
    }

    @Override
    public Word nextSolution() {
        Random rnd = new Random();
        Collection<Word> words = source.getAll();
        return new ArrayList<>(words).get(rnd.nextInt(words.size()));
    }
}
