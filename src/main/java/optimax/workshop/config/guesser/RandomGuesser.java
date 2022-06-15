package optimax.workshop.config.guesser;

import optimax.workshop.config.generator.RandomLetterSolutionGenerator;
import optimax.workshop.config.generator.WordSourceSolutionGenerator;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.SolutionGenerator;
import optimax.workshop.runner.WordAccepter;
import optimax.workshop.runner.WordSource;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RandomGuesser implements Guesser {
    private SolutionGenerator generator;

    @Override
    public void init(WordSource source, WordAccepter accepter) {
        if (source.getAll().isEmpty()){
            generator = new RandomLetterSolutionGenerator();
        }else {
            generator = new WordSourceSolutionGenerator(source);
        }
    }

    @Override
    public Word nextGuess() {
        return generator.nextSolution();
    }

    @Override
    public void match(Word guess, MatchResult result) {
        // ignore
    }
}
