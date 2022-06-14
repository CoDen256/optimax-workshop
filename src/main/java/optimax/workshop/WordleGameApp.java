package optimax.workshop;

import java.util.function.IntPredicate;
import java.util.function.Supplier;
import optimax.workshop.config.accepter.WordSourceAccepter;
import optimax.workshop.config.generator.WordSourceSolutionGenerator;
import optimax.workshop.config.guesser.UserInputGuesser;
import optimax.workshop.config.matcher.StandardMatcher;
import optimax.workshop.config.observer.ConsoleLogger;
import optimax.workshop.config.runner.RepeatedRunner;
import optimax.workshop.config.runner.WordleRunner;
import optimax.workshop.config.source.FileWordSource;
import optimax.workshop.core.WordleGame;
import optimax.workshop.core.dictionary.SolutionGenerator;
import optimax.workshop.core.dictionary.WordAccepter;
import optimax.workshop.core.dictionary.WordSource;
import optimax.workshop.core.matcher.WordMatcher;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.GameRunner;
import optimax.workshop.runner.Guesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordleGameApp {

    public static final int MAX_ATTEMPTS = 6;
    public static final IntPredicate RUN_CONDITION = i -> i <= 1;
    public static final String WORDS_SOURCE = "/words.txt";

    public static void main(String[] args) {
        GameRunner runner = createRunner();
        runner.run();
    }
    private static GameRunner createRunner() {
        WordSource source = new FileWordSource(WORDS_SOURCE);
        WordSource sourceVisibleToGuesser = source;
        WordAccepter accepter = new WordSourceAccepter(source);
        SolutionGenerator generator = new WordSourceSolutionGenerator(source);

        Supplier<Guesser> guesser = () -> new UserInputGuesser();

        WordMatcher matcher = new StandardMatcher();
        GameObserver observer = new ConsoleLogger();
        GameRunner runner = new WordleRunner(
                () -> new WordleGame(MAX_ATTEMPTS, generator.nextSolution(), accepter, matcher),
                () -> observer,
                () -> {
                    Guesser g = guesser.get();
                    g.init(sourceVisibleToGuesser, accepter);
                    return g;
                }
        );
        return new RepeatedRunner(RUN_CONDITION, runner);
    }
}
