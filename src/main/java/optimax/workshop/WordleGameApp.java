package optimax.workshop;

import java.util.Collection;
import optimax.workshop.config.FileWordLoader;
import optimax.workshop.config.accepter.CollectionAccepter;
import optimax.workshop.config.generator.CollectionSolutionGenerator;
import optimax.workshop.config.guesser.RegexBasedGuesser;
import optimax.workshop.config.matcher.StandardMatcher;
import optimax.workshop.config.observer.AggregatedFullObserver;
import optimax.workshop.config.observer.ConsolePrettyPrinter;
import optimax.workshop.config.observer.Greeter;
import optimax.workshop.config.observer.ScorePrettyPrinter;
import optimax.workshop.config.observer.Scorer;
import optimax.workshop.config.runner.GameRunnerBuilder;
import optimax.workshop.core.Word;
import optimax.workshop.runner.GameRunner;

public class WordleGameApp {

    public static void main(String[] args) {
        createRunner().run();
    }
    private static GameRunner createRunner() {
        Collection<Word> solutions = FileWordLoader.load(path("/words-mini.txt"));
        Collection<Word> accepted = FileWordLoader.load(path("/words.txt"));
        return new GameRunnerBuilder()
                // The guesser (created each time newly for each game)
                .guesser(() -> new RegexBasedGuesser(new StandardMatcher()))

                // Solutions that are visible to guesser
                .solutionsVisibleToGuesser(solutions)
                // Accepted words that are visible to guesser
                .acceptedVisibleToGuesser(accepted)

                // The generator of the solution for each game based on the solution set
                .generator(new CollectionSolutionGenerator(solutions))
                // Accepter based on the accepted words
                .accepter(new CollectionAccepter(accepted))

                .addObserver(new Scorer(
                        new ConsolePrettyPrinter(true),
//                        new ConsoleMinimalPrinter(false, false, true, false),
                        new AggregatedFullObserver(
                                new ScorePrettyPrinter(),
                                new Greeter()
                        )
                ))

                .maxAttempts(6)                 // Max attempts per game
                .runLimit(10)           // Total amount of games

                .matcher(new StandardMatcher()) // Word comparing strategy
                .build();
    }

    private static String path(String resource){
        return WordleGameApp.class.getResource(resource).getPath();
    }
}
