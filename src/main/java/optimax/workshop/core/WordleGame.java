package optimax.workshop.core;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.core.matcher.WordMatcher;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public final class WordleGame {

    /** The solution of the game */
    private final Word solution;
    /** Submitted guesses */
    private final Collection<Word> submitted = new ArrayList<>();

    /** Computes the {@link MatchResult} for the given guess in comparison to the solution */
    private final WordMatcher matcher;
    /** Max amount of attempts that could be made */
    private final int maxAttempts;

    public WordleGame(int maxAttempts, Word solution, WordMatcher matcher) {
        this.solution = requireNonNull(solution);
        this.matcher = requireNonNull(matcher);
        this.maxAttempts = maxAttempts;
        if (maxAttempts <= 0){
            throw new IllegalArgumentException(format("Max attempts cannot be less than or equal to zero, but was: %d", maxAttempts));
        }
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

    public Word getSolution() {
        return solution;
    }

    public MatchResult submit(Word guess) {
        requireNonNull(guess);
        if (isSolved())
            throw new IllegalStateException(format("Game is solved, no more guesses allowed. The solution is: %s", solution));
        if (isFinished())
            throw new IllegalStateException(format("Game is finished, no more guesses allowed. Guess submitted: %s", guess));
        submitted.add(guess);
        return matcher.match(solution, guess);
    }
}
