package optimax.workshop.config.runner;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import optimax.workshop.config.observer.AggregatedObserver;
import optimax.workshop.core.Word;
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
public class GameRunnerBuilder {

    // basic
    private WordMatcher matcher;
    private int maxAttempts;

    // constant
    private Collection<Word> visibleSolutions;
    private Collection<Word> visibleAccepted;
    private SolutionGenerator generator;
    private WordAccepter accepter;

    // game specific
    private Supplier<Guesser> guesser;
    private final List<Supplier<GameObserver>> observers = new ArrayList<>();
    private IntPredicate runCondition;


    public GameRunnerBuilder acceptedVisibleToGuesser(Collection<Word> accepted) {
        this.visibleAccepted = new ArrayList<>(accepted);
        return this;
    }

    public GameRunnerBuilder solutionsVisibleToGuesser(Collection<Word> solutions) {
        this.visibleSolutions = new ArrayList<>(solutions);
        return this;
    }

    public GameRunnerBuilder accepter(WordAccepter accepter) {
        this.accepter = accepter;
        return this;
    }

    public GameRunnerBuilder generator(SolutionGenerator generator) {
        this.generator = generator;
        return this;
    }

    public GameRunnerBuilder guesser(Supplier<Guesser> guesser) {
        this.guesser = guesser;
        return this;
    }

    public GameRunnerBuilder addObserver(Supplier<GameObserver> observer) {
        this.observers.add(observer);
        return this;
    }

    public GameRunnerBuilder addObserver(GameObserver observer) {
        return addObserver(() -> observer);
    }


    public GameRunnerBuilder matcher(WordMatcher matcher) {
        this.matcher = matcher;
        return this;
    }

    public GameRunnerBuilder maxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        return this;
    }

    public GameRunnerBuilder runLimit(int times){
        return runWhile(i -> i < times);
    }

    public GameRunnerBuilder runWhile(IntPredicate runCondition) {
        this.runCondition = runCondition;
        return this;
    }

    public GameRunner build(){
        return new RepeatedRunner(requireNonNull(runCondition), this::buildRunner);
    }

    private GameRunner buildRunner() {
        return new WordleRunner(
                maxAttempts,
                requireNonNull(visibleSolutions, "You have to set visible to guesser solutions"),
                requireNonNull(visibleAccepted, "You have to set visible to accepted words"),
                requireNonNull(generator, "You have to set solution generator (e.g. accepter(new CollectionSolutionGenerator(src))"),
                requireNonNull(accepter, "You have to set the word accepter (e.g. accepter(new CollectionAccepter(src))"),
                requireNonNull(guesser.get(), "You have to provide a guesser (e.g. guesser(new GuesserImpl())"),
                buildObserver(),
                requireNonNull(matcher, "You have to provide a word matcher ( `matcher(new StandardMatcher())` call is probably missing)")
        );
    }
    private GameObserver buildObserver() {
        return new AggregatedObserver(observers.stream()
                .map(Objects::requireNonNull)
                .map(Supplier::get)
                .collect(Collectors.toList()));
    }

}
