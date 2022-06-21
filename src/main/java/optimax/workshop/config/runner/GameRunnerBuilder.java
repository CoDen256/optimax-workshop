package optimax.workshop.config.runner;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import optimax.workshop.config.guesser.RotatingGuesser;
import optimax.workshop.config.observer.AggregatedGameObserver;
import optimax.workshop.core.Word;
import optimax.workshop.runner.WordMatcher;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.GameRunner;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.SolutionGenerator;
import optimax.workshop.runner.WordAccepter;

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
    private int runCount;


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


    public GameRunnerBuilder matcher(WordMatcher matcher) {
        this.matcher = matcher;
        return this;
    }

    public GameRunnerBuilder maxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        return this;
    }

    public GameRunnerBuilder runLimit(int times){
        this.runCount = times;
        return this;
    }

    public GameRunner build(){
        GameObserver observer = buildObserver();
        Supplier<Guesser> guesserSupplier = buildGuesserFactory();
        return new RepeatedRunner(runCount, observer, () -> buildRunner(observer, guesserSupplier));
    }

    private GameRunner buildRunner(GameObserver observer, Supplier<Guesser> guesserSupplier) {
        return new WordleRunner(
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

    private Supplier<Guesser> buildGuesserFactory(){
        if (guessers.isEmpty()){
            throw new IllegalArgumentException("You have to provide a guesser (e.g. guesser(new GuesserImpl())");
        }
        return new RotatingGuesser(runCount, guessers);
    }
    private GameObserver buildObserver() {
        return new AggregatedGameObserver(observers.stream()
                .map(Objects::requireNonNull)
                .map(Supplier::get)
                .collect(Collectors.toList()));
    }

}
