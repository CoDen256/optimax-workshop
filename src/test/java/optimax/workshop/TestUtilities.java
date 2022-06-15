package optimax.workshop;

import java.util.stream.Stream;
import optimax.workshop.config.generator.RandomLetterSolutionGenerator;
import optimax.workshop.core.Word;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class TestUtilities {
    public static Word word(String word) {
        return new Word(word);
    }

    public static Stream<Word> generateWords(int limit){
        RandomLetterSolutionGenerator gen = new RandomLetterSolutionGenerator();
        return Stream.generate(gen::nextSolution).limit(limit);
    }

}
