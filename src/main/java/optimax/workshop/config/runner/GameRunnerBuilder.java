package optimax.workshop.config.runner;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.core.match.WordMatcher;
import optimax.workshop.run.RepeatedWordleRunner;
import optimax.workshop.run.WordleRunner;
import optimax.workshop.run.SingleWordleRunner;
import optimax.workshop.run.guesser.Guesser;
import optimax.workshop.run.guesser.RotatingGuesserFactory;
import optimax.workshop.run.observer.AggregatedGameObserver;
import optimax.workshop.run.observer.GameObserver;
import optimax.workshop.run.words.SolutionGenerator;
import optimax.workshop.run.words.WordAccepter;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class GameRunnerBuilder {

    private WordMatcher matcher;
    private int maxAttempts;
    private Collection<Word> visibleSolutions;
    private Collection<Word> visibleAccepted;
    private SolutionGenerator generator;
    private WordAccepter accepter;
    private final List<Supplier<Guesser>> guessers = new ArrayList<>();
    private final List<Supplier<GameObserver>> observers = new ArrayList<>();
    private int limit;

    /**
     * Set the accepted words, that are visible to the {@link Guesser}. May differ
     * from the actual accepted words set used in the {@link WordAccepter}
     *
     * @param accepted
     *         the accepted words set provided to the {@link Guesser}
     */
    public GameRunnerBuilder acceptedVisibleToGuesser(Collection<Word> accepted) {
        this.visibleAccepted = new ArrayList<>(accepted);
        return this;
    }

    /**
     * Set the solutions, that are visible to the {@link Guesser}. May differ
     * from the actual solution set used in the {@link SolutionGenerator}
     *
     * @param solutions
     *         the solution set provided to the {@link Guesser}
     */
    public GameRunnerBuilder solutionsVisibleToGuesser(Collection<Word> solutions) {
        this.visibleSolutions = new ArrayList<>(solutions);
        return this;
    }

    /**
     * Set the {@link WordAccepter}, that will check what words will be accepted
     *
     * @param accepter
     *         the word accepter
     */
    public GameRunnerBuilder accepter(WordAccepter accepter) {
        this.accepter = accepter;
        return this;
    }

    /**
     * Set the {@link SolutionGenerator} to generate solutions
     *
     * @param generator
     *         the solution generator
     */
    public GameRunnerBuilder generator(SolutionGenerator generator) {
        this.generator = generator;
        return this;
    }

    /**
     * Adds a {@link Guesser} factory to run. All the given guessers will be rotated throughout the total number of runs
     * by {@link RotatingGuesserFactory}
     *
     * @param guesser
     *         the guesser supplier
     * @see RotatingGuesserFactory
     */
    public GameRunnerBuilder addGuesser(Supplier<Guesser> guesser) {
        this.guessers.add(guesser);
        return this;
    }

    public GameRunnerBuilder addObserver(Supplier<GameObserver> observer) {
        this.observers.add(observer);
        return this;
    }

    public GameRunnerBuilder addObserver(GameObserver observer) {
        return addObserver(() -> observer);
    }

    /**
     * Sets the {@link WordMatcher} that will be used to calculate {@link MatchResult}
     * based on the guess and solution {@link Word}s.
     *
     * @param matcher
     *         the word matcher
     */
    public GameRunnerBuilder matcher(WordMatcher matcher) {
        this.matcher = matcher;
        return this;
    }

    /**
     * Sets the maximal number of attempts per game.
     *
     * @param maxAttempts
     *         the number of attempts per each game
     */
    public GameRunnerBuilder maxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        return this;
    }

    /**
     * Sets the total number of game runs.
     *
     * @param limit
     *         the number of runs
     */
    public GameRunnerBuilder runLimit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Builds the {@link WordleRunner} that runs specific number of times and creates each game
     * based on the previously set parameters
     */
    public WordleRunner build() {
        GameObserver observer = buildObserver();
        Supplier<Guesser> guesserSupplier = buildGuesserFactory();
        return new RepeatedWordleRunner(limit, observer, () -> buildRunner(observer, guesserSupplier));
    }

    private WordleRunner buildRunner(GameObserver observer, Supplier<Guesser> guesserSupplier) {
        return new SingleWordleRunner(
                maxAttempts,
                requireNonNull(visibleSolutions, "You have to set visible to guesser solutions"),
                requireNonNull(visibleAccepted, "You have to set visible to accepted words"),
                requireNonNull(generator, "You have to set solution generator (e.g. accepter(new CollectionSolutionGenerator(src))"),
                requireNonNull(accepter, "You have to set the word accepter (e.g. accepter(new CollectionAccepter(src))"),
                guesserSupplier.get(),
                observer,
                requireNonNull(matcher, "You have to provide a word matcher ( `matcher(new StandardMatcher())` call is probably missing)")
        );
    }

    private Supplier<Guesser> buildGuesserFactory() {
        if (guessers.isEmpty()) {
            throw new IllegalArgumentException("You have to provide a guesser (e.g. guesser(new GuesserImpl())");
        }
        return new RotatingGuesserFactory(limit, guessers);
    }

    private GameObserver buildObserver() {
        return new AggregatedGameObserver(observers.stream()
                .map(Objects::requireNonNull)
                .map(Supplier::get)
                .collect(Collectors.toList()));
    }

}
