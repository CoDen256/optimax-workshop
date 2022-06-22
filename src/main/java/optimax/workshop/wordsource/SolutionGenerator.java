package optimax.workshop.wordsource;

import optimax.workshop.core.Word;
import optimax.workshop.guesser.Guesser;

/**
 * The {@code SolutionGenerator} generates each time a new solution, that has to be guessed.
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface SolutionGenerator {
    /**
     * Generates next solution to be guessed by the {@link Guesser}. It is called every time
     * the game is started and a new solutions has to be generated
     *
     * @return the next solution {@link Word}
     */
    Word nextSolution();
}
