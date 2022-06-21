package optimax.workshop.config.runner;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.core.matcher.MatchType;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.GameRunner;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.SolutionGenerator;
import optimax.workshop.runner.WordAccepter;
import optimax.workshop.runner.WordMatcher;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordleRunner implements GameRunner {

    private final int maxAttempts;
    private final Collection<Word> visibleSolutions;
    private final Collection<Word> visibleAccepted;
    private final WordAccepter accepter;
    private final SolutionGenerator generator;
    private final Guesser guesser;
    private final GameObserver observer;
    private final WordMatcher matcher;

    public WordleRunner(int maxAttempts,
                        Collection<Word> visibleSolutions,
                        Collection<Word> visibleAccepted,
                        SolutionGenerator generator,
                        WordAccepter accepter,
                        Guesser guesser,
                        GameObserver observer,
                        WordMatcher matcher) {
        this.maxAttempts = maxAttempts;
        this.generator = requireNonNull(generator);
        this.visibleSolutions = requireNonNull(visibleSolutions);
        this.visibleAccepted = requireNonNull(visibleAccepted);
        this.matcher = requireNonNull(matcher);
        this.observer = requireNonNull(observer);
        this.guesser = requireNonNull(guesser);
        this.accepter = requireNonNull(accepter);
    }

    public void run(){
        Word solution = generator.nextSolution();

        guesser.init(visibleSolutions, visibleAccepted);
        observer.onGameCreated(solution, guesser, accepter);

        verifyGame(solution, maxAttempts);
        boolean isSolved = false;
        for (int i = 0; i < maxAttempts && !isSolved; i++) {
            observer.onGuessExpected();
            Word guess = guesser.nextGuess();

            isSolved = processGuess(solution, guess);
        }
        observer.onGameFinished(isSolved);
    }

    private boolean processGuess(Word solution, Word guess) {
        if (accepter.isNotAccepted(guess)) {
            observer.onGuessRejected(guess);
            return false;
        }
        return submitGuess(solution, guess);
    }

    private boolean submitGuess(Word solution, Word guess) {
        MatchResult result = matcher.match(solution, guess);
        observer.onGuessSubmitted(guess, result);
        guesser.match(guess, result);
        return isSolved(result);
    }

    private boolean isSolved(MatchResult result){
        return result
                .getMatches()
                .stream()
                .allMatch(m -> m.getType() == MatchType.CORRECT);
    }

    private void verifyGame(Word solution, int maxAttempts){
        if (accepter.isNotAccepted(solution))
            throw new IllegalArgumentException(format("Solution (%s) is not accepted", solution));
        if (maxAttempts <= 0){
            throw new IllegalArgumentException(format("Max attempts cannot be less than or equal to zero, but was: %d", maxAttempts));
        }
    }
}
