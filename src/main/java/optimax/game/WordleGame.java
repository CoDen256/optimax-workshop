package optimax.game;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import optimax.game.accepter.WordAccepter;
import optimax.game.matcher.MatchResult;
import optimax.game.matcher.WordMatcher;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public final class WordleGame {

    public static final int MAX_ATTEMPTS = 6;
    /** The solution of the game */
    private final Word solution;
    private final Collection<Word> submitted = new ArrayList<>();
    /** Checks whether the given guess is accepted */
    private final WordAccepter accepter;
    /** Computes the {@link MatchResult} for the given guess in comparison with the solution */
    private final WordMatcher matcher;

    public WordleGame(Word solution, WordAccepter accepter, WordMatcher matcher) {
        this.accepter = requireNonNull(accepter);
        this.solution = requireNonNull(solution);
        this.matcher = requireNonNull(matcher);
        if (accepter.isNotAccepted(solution))
            throw new IllegalArgumentException(format("Solution (%s) is not accepted", solution));
    }

    public boolean isFinished() {
        return submitted.size() == MAX_ATTEMPTS || isSolved();
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
