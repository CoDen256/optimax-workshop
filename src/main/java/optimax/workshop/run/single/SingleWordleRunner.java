package optimax.workshop.run.single;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.core.match.MatchType;
import optimax.workshop.run.WordleRunner;
import optimax.workshop.guesser.Guesser;
import optimax.workshop.wordsource.SolutionGenerator;
import optimax.workshop.wordsource.WordAccepter;
import optimax.workshop.core.match.WordMatcher;

/**
 * A single {@link WordleRunner} that runs the Wordle Game only once, dispatching
 * all the necessary information to the {@link GameLifecycleObserver}
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class SingleWordleRunner implements WordleRunner {

    private final int maxAttempts;
    private final Collection<Word> visibleSolutions;
    private final Collection<Word> visibleAccepted;
    private final WordAccepter accepter;
    private final SolutionGenerator generator;
    private final Guesser guesser;
    private final GameLifecycleObserver observer;
    private final WordMatcher matcher;
    private final boolean failOnRejected;

    public SingleWordleRunner(int maxAttempts,
                              boolean failOnRejected,
                              Collection<Word> visibleSolutions,
                              Collection<Word> visibleAccepted,
                              SolutionGenerator generator,
                              WordAccepter accepter,
                              Guesser guesser,
                              GameLifecycleObserver observer,
                              WordMatcher matcher) {
        this.maxAttempts = maxAttempts;
        this.failOnRejected = failOnRejected;
        this.generator = requireNonNull(generator);
        this.visibleSolutions = new ArrayList<>(requireNonNull(visibleSolutions));
        this.visibleAccepted = new ArrayList<>(requireNonNull(visibleAccepted));
        this.matcher = requireNonNull(matcher);
        this.observer = requireNonNull(observer);
        this.guesser = requireNonNull(guesser);
        this.accepter = requireNonNull(accepter);
    }

    public void run() {
        Word solution = generator.nextSolution();

        guesser.init(visibleSolutions, visibleAccepted);
        observer.onGameCreated(solution, guesser, accepter);

        verifyGame(solution, maxAttempts);
        boolean isSolved = false;
        int attempt = 0;
        while (attempt < maxAttempts && !isSolved) {
            observer.onGuessExpected();
            Word guess = requireNonNull(guesser.nextGuess(), "The submitted guess must not be null");

            if (accepter.isNotAccepted(guess)) {
                observer.onGuessRejected(guess);
                if (failOnRejected)
                    throw new IllegalStateException(String.format("Guesser %s has submitted invalid guess %s", guesser.name(), guess));
            } else {
                isSolved = submitGuess(solution, guess);
                attempt++;
            }
        }
        observer.onGameFinished(isSolved);
    }

    private boolean submitGuess(Word solution, Word guess) {
        MatchResult result = matcher.match(solution, guess);
        observer.onGuessSubmitted(guess, result);
        guesser.match(guess, result);
        return isSolved(result);
    }

    private boolean isSolved(MatchResult result) {
        return result
                .getMatches()
                .stream()
                .allMatch(m -> m.getType() == MatchType.CORRECT);
    }

    private void verifyGame(Word solution, int maxAttempts) {
        if (accepter.isNotAccepted(solution))
            throw new IllegalArgumentException(format("Solution (%s) is not accepted", solution));
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException(format("Max attempts cannot be less than or equal to zero, but was: %d", maxAttempts));
        }
    }
}
