package optimax.workshop.config.generator;

import java.util.Random;
import optimax.workshop.core.Word;
import optimax.workshop.runner.SolutionGenerator;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RandomLetterSolutionGenerator implements SolutionGenerator {
    private final Random random = new Random();

    @Override
    public Word nextSolution() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(generateNextChar());
        }
        return new Word(builder.toString());
    }

    private char generateNextChar(){
        return (char) ('a' + random.nextInt(26));
    }
}
