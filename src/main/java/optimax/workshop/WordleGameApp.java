package optimax.workshop;

import static java.lang.String.format;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import optimax.workshop.config.accepter.WordSourceAccepter;
import optimax.workshop.config.generator.WordSourceSolutionGenerator;
import optimax.workshop.config.guesser.RegexBasedGuesser;
import optimax.workshop.config.guesser.SimpleGuesser;
import optimax.workshop.config.matcher.StandardMatcher;
import optimax.workshop.config.observer.AggregatedObserver;
import optimax.workshop.config.observer.ConsoleMinimalPrinter;
import optimax.workshop.config.observer.ConsolePrettyPrinter;
import optimax.workshop.config.observer.ScoringObserver;
import optimax.workshop.config.runner.RepeatedRunner;
import optimax.workshop.config.runner.WordleRunner;
import optimax.workshop.config.source.FileWordSource;
import optimax.workshop.core.Word;
import optimax.workshop.core.WordleGame;
import optimax.workshop.core.matcher.WordMatcher;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.GameRunner;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.SolutionGenerator;
import optimax.workshop.runner.WordAccepter;
import optimax.workshop.runner.WordSource;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordleGameApp {

    private static final int MAX_ATTEMPTS = 6;
    private static final int RUN_TIMES = 1000;
    private static final IntPredicate RUN_CONDITION = i -> i < RUN_TIMES;
    private static final String WORDS_SOURCE = "/words.txt";
    private static final String WORDS_SOURCE_PATH = WordleGameApp.class.getResource(WORDS_SOURCE).getPath();

    public static void main(String[] args) {
        GameRunner runner = createRunner();
        runner.run();
    }
    private static GameRunner createRunner() {
        WordSource source = new FileWordSource(WORDS_SOURCE_PATH);
        WordSource sourceVisibleToGuesser = source;
        WordAccepter accepter = new WordSourceAccepter(source);
        SolutionGenerator generator = new WordSourceSolutionGenerator(source);

        Supplier<Guesser> guesser = () -> new SimpleGuesser();

        WordMatcher matcher = new StandardMatcher();
        GameObserver observer = new AggregatedObserver(List.of(
                new ConsoleMinimalPrinter()
                ,new ScoringObserver()
        ));

        return new RepeatedRunner(RUN_CONDITION, () -> {
            Word newSolution = verify(accepter, generator.nextSolution());

            WordleGame newGame = new WordleGame(MAX_ATTEMPTS, newSolution, matcher);

            Guesser newGuesser = guesser.get();
            newGuesser.init(sourceVisibleToGuesser, accepter);

            return new WordleRunner(newGame, observer, newGuesser, accepter);
        });
    }

    private static Word verify(WordAccepter accepter, Word solution){
        if (accepter.isNotAccepted(solution))
            throw new IllegalArgumentException(format("Solution (%s) is not accepted", solution));
        return solution;
    }
}
