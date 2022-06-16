package optimax.workshop;

import java.util.Collection;
import optimax.workshop.config.accepter.CollectionAccepter;
import optimax.workshop.config.generator.CollectionSolutionGenerator;
import optimax.workshop.config.guesser.RegexBasedGuesser;
import optimax.workshop.config.matcher.StandardMatcher;
import optimax.workshop.config.observer.ConsoleMinimalPrinter;
import optimax.workshop.config.observer.ScoringObserver;
import optimax.workshop.config.runner.GameRunnerBuilder;
import optimax.workshop.config.FileWordLoader;
import optimax.workshop.core.Word;
import optimax.workshop.runner.GameRunner;

public class WordleGameApp {

    public static void main(String[] args) {
        createRunner().run();
    }
    private static GameRunner createRunner() {
        Collection<Word> solutions = FileWordLoader.load(resource("/words.txt"));
        Collection<Word> accepted = FileWordLoader.load(resource("/words.txt"));
        return new GameRunnerBuilder()
                // The guesser (created each time newly for each game)
                .guesser(() -> new RegexBasedGuesser())

                // Solutions that are visible to guesser based on actual solutions
                .solutionsVisibleToGuesser(solutions)
                // Accepted words that are visible to guesser based on actual accepted words
                .acceptedVisibleToGuesser(accepted)

                // The generator of the solution for each game based on the solution set
                .generator(new CollectionSolutionGenerator(solutions))
                // Accepter based on the accepted words
                .accepter(new CollectionAccepter(accepted))

                .addObserver(new ConsoleMinimalPrinter()) // Prints only the results
                .addObserver(new ScoringObserver())       // Prints the results and calculates

                .maxAttempts(6)                 // Max attempts per game
                .runLimit(1000)           // Total amount of games

                .matcher(new StandardMatcher()) // Word comparing strategy
                .build();
    }

    private static String resource(String resource){
        return WordleGameApp.class.getResource(resource).getPath();
    }
}
