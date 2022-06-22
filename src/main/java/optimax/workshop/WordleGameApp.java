package optimax.workshop;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import optimax.workshop.config.FileWordLoader;
import optimax.workshop.config.RepeatedWordleRunnerBuilder;
import optimax.workshop.config.accepter.CollectionAccepter;
import optimax.workshop.config.generator.CollectionSolutionGenerator;
import optimax.workshop.config.guesser.RandomGuesser;
import optimax.workshop.config.guesser.SimpleGuesser;
import optimax.workshop.config.observer.ConsoleMinimalPrinter;
import optimax.workshop.config.observer.ScorePrettyPrinter;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.StandardMatcher;
import optimax.workshop.run.WordleRunner;

public class WordleGameApp {

    public static void main(String[] args) {
        createRunner().run();
    }

    private static WordleRunner createRunner() {
        // The solutions source
        Collection<Word> solutions = FileWordLoader.load(path("/words-mini.txt"));
        // The accepted words
        Collection<Word> accepted = FileWordLoader.load(path("/words-distinct.txt"));
        return new RepeatedWordleRunnerBuilder()
                // The guesser (created each time newly for each game)
                .addGuesser(() -> new SimpleGuesser())
                .addGuesser(() -> new RandomGuesser())

                // Solutions that are visible to the guessers
                .solutionsVisibleToGuesser(solutions)
                // Accepted words that are visible to the guessers
                .acceptedVisibleToGuesser(accepted)

                // The generator of the solution for each game based on the solution set
                .generator(new CollectionSolutionGenerator(solutions))
                // Accepter based on the accepted words
                .accepter(new CollectionAccepter(accepted))

                .observer(new ScorePrettyPrinter())
                .observer(new ConsoleMinimalPrinter(false, false, true, false))
//                .observer(new ConsoleFullPrinter(true))

                .maxAttempts(6)            // Max attempts per game
                .runLimit(1000)           // Total amount of games

                .matcher(new StandardMatcher()) // Word comparing/matching strategy
                .build();
    }

    /** Helper method to get the pass in the resource folder */
    private static String path(String resource) {
        return requireNonNull(WordleGameApp.class.getResource(resource), "Invalid resource filename").getPath();
    }
}
