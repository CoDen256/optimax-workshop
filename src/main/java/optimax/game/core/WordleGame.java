package optimax.game.core;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import optimax.game.core.accepter.WordAccepter;
import optimax.game.core.matcher.MatchResult;
import optimax.game.core.matcher.WordMatcher;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public final class WordleGame {

    private static final int DEFAULT_MAX_ATTEMPTS = 6;
    /** The solution of the game */
    private final Word solution;
    private final Collection<Word> submitted = new ArrayList<>();
    /** Checks whether the given guess is accepted */
    private final WordAccepter accepter;
    /** Computes the {@link MatchResult} for the given guess in comparison with the solution */
    private final WordMatcher matcher;
    private final int maxAttempts;

    public WordleGame(int maxAttempts, Word solution, WordAccepter accepter, WordMatcher matcher) {
        this.accepter = requireNonNull(accepter);
        this.solution = requireNonNull(solution);
        this.matcher = requireNonNull(matcher);
        this.maxAttempts = maxAttempts;
        if (maxAttempts <= 0){
            throw new IllegalArgumentException(format("Max attempts cannot be less than or equal to zero, but was: %d", maxAttempts));
        }
        if (accepter.isNotAccepted(solution))
            throw new IllegalArgumentException(format("Solution (%s) is not accepted", solution));
    }

    public WordleGame(Word solution, WordAccepter accepter, WordMatcher matcher){
        this(DEFAULT_MAX_ATTEMPTS, solution, accepter, matcher);
    }

    public boolean isFinished() {
        return submitted.size() == maxAttempts || isSolved();
    }

    public boolean isSolved() {
        return submitted.contains(solution);
    }

    public Collection<Word> getSubmitted() {
        return Collections.unmodifiableCollection(submitted);
    }

    public MatchResult submit(Word guess) {
        requireNonNull(guess);
        if (isSolved())
            throw new IllegalStateException(format("Game is solved, no more guesses allowed. The solution is: %s", solution));
        if (isFinished())
            throw new IllegalStateException(format("Game is finished, no more guesses allowed. Guess submitted: %s", guess));
        if (accepter.isNotAccepted(guess))
            throw new IllegalArgumentException(format("Word %s is not accepted", guess));
        submitted.add(guess);
        return matcher.match(solution, guess);
    }
}
