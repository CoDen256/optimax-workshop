package optimax.workshop.config.guesser;

import java.util.Collection;
import optimax.workshop.config.generator.RandomLetterSolutionGenerator;
import optimax.workshop.config.generator.CollectionSolutionGenerator;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.run.guesser.Guesser;
import optimax.workshop.run.words.SolutionGenerator;

/**
 * The {@code RandomGuesser} makes guesses randomly. If the given solutions is empty
 * it uses {@link RandomLetterSolutionGenerator} otherwise, it uses {@link CollectionSolutionGenerator} based
 * on the given collection
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RandomGuesser implements Guesser {
    private SolutionGenerator generator;

    @Override
    public void init(Collection<Word> solutions, Collection<Word> accepted) {
        if (solutions.isEmpty()) {
            generator = new RandomLetterSolutionGenerator();
        } else {
            generator = new CollectionSolutionGenerator(solutions);
        }
    }

    @Override
    public Word nextGuess() {
        return generator.nextSolution();
    }

    @Override
    public void match(Word guess, MatchResult result) {/* ignore */}
}
