package optimax.workshop.config;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.core.match.WordMatcher;
import optimax.workshop.guesser.Guesser;
import optimax.workshop.guesser.RotatingGuesserFactory;
import optimax.workshop.run.WordleRunner;
import optimax.workshop.run.full.FullLifecycleObserver;
import optimax.workshop.run.full.GameRecorder;
import optimax.workshop.run.multiple.AppObserver;
import optimax.workshop.run.multiple.RepeatedWordleRunner;
import optimax.workshop.run.single.GameLifecycleObserver;
import optimax.workshop.run.single.GameObserver;
import optimax.workshop.run.single.SingleWordleRunner;
import optimax.workshop.wordsource.SolutionGenerator;
import optimax.workshop.wordsource.WordAccepter;

/**
 * Builds a {@link RepeatedWordleRunner}
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RepeatedWordleRunnerBuilder {

    private WordMatcher matcher;
    private int maxAttempts;
    private Collection<Word> visibleSolutions;
    private Collection<Word> visibleAccepted;
    private SolutionGenerator generator;
    private WordAccepter accepter;

    private GameObserver gameObserver;
    private AppObserver appObserver;
    private final List<Supplier<Guesser>> guessers = new ArrayList<>();
    private int limit;

    private boolean failOnRejected = false;

    /**
     * Set the accepted words, that are visible to the {@link Guesser}. May differ
     * from the actual accepted words set used in the {@link WordAccepter}
     *
     * @param accepted
     *         the accepted words set provided to the {@link Guesser}
     */
    public RepeatedWordleRunnerBuilder acceptedVisibleToGuesser(Collection<Word> accepted) {
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
    public RepeatedWordleRunnerBuilder solutionsVisibleToGuesser(Collection<Word> solutions) {
        this.visibleSolutions = new ArrayList<>(solutions);
        return this;
    }

    /**
     * Set the {@link WordAccepter}, that will check what words will be accepted
     *
     * @param accepter
     *         the word accepter
     */
    public RepeatedWordleRunnerBuilder accepter(WordAccepter accepter) {
        this.accepter = accepter;
        return this;
    }

    /**
     * Set the {@link SolutionGenerator} to generate solutions
     *
     * @param generator
     *         the solution generator
     */
    public RepeatedWordleRunnerBuilder generator(SolutionGenerator generator) {
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
    public RepeatedWordleRunnerBuilder addGuesser(Supplier<Guesser> guesser) {
        this.guessers.add(guesser);
        return this;
    }

    /**
     * Sets the {@link GameObserver} that will observe a single game run
     *
     * @param gameObserver
     *         the single game run observer
     */
    public RepeatedWordleRunnerBuilder observer(GameObserver gameObserver) {
        this.gameObserver = gameObserver;
        return this;
    }

    /**
     * Sets the {@link GameObserver} that will observe a multiple game runs
     *
     * @param appObserver
     *         the multiple game run observer
     */
    public RepeatedWordleRunnerBuilder observer(AppObserver appObserver) {
        this.appObserver = appObserver;
        return this;
    }

    /**
     * Sets the {@link WordMatcher} that will be used to calculate {@link MatchResult}
     * based on the guess and solution {@link Word}s.
     *
     * @param matcher
     *         the word matcher
     */
    public RepeatedWordleRunnerBuilder matcher(WordMatcher matcher) {
        this.matcher = matcher;
        return this;
    }

    /**
     * Sets the maximal number of attempts per game.
     *
     * @param maxAttempts
     *         the number of attempts per each game
     */
    public RepeatedWordleRunnerBuilder maxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        return this;
    }

    /**
     * Sets the total number of game runs.
     *
     * @param limit
     *         the number of runs
     */
    public RepeatedWordleRunnerBuilder runLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public RepeatedWordleRunnerBuilder failOnRejected(boolean failOnRejected){
        this.failOnRejected = failOnRejected;
        return this;
    }

    /**
     * Builds the {@link WordleRunner} that runs specific number of times and creates each game
     * based on the previously set parameters
     */
    public WordleRunner build() {
        FullLifecycleObserver observer = new GameRecorder(appObserver, gameObserver);
        Supplier<Guesser> guesserFactory = buildGuesserFactory();
        return new RepeatedWordleRunner(limit, observer, () -> buildSingleRunner(observer, guesserFactory));
    }

    private WordleRunner buildSingleRunner(GameLifecycleObserver observer, Supplier<Guesser> guesserFactory) {
        return new SingleWordleRunner(
                maxAttempts,
                failOnRejected,
                requireNonNull(visibleSolutions, "You have to set visible to guesser solutions"),
                requireNonNull(visibleAccepted, "You have to set visible to accepted words"),
                requireNonNull(generator, "You have to set solution generator (e.g. accepter(new CollectionSolutionGenerator(src))"),
                requireNonNull(accepter, "You have to set the word accepter (e.g. accepter(new CollectionAccepter(src))"),
                guesserFactory.get(),
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

}
