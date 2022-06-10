package optimax;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import optimax.accepter.WordAccepter;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public final class WordleGame {

    private final Word solution;
    private int counter = 0;
    private final Collection<Word> submitted = new ArrayList<>();
    private final WordAccepter accepter;

    public WordleGame(Word solution, WordAccepter accepter) {
        this.accepter = requireNonNull(accepter);
        this.solution = requireNonNull(solution);
        if (!accepter.accept(solution)){
            throw new IllegalArgumentException(String.format("Solution (%s) is not accepted", solution));
        }
    }

    public boolean isFinished() {
        return counter == 5 || submitted.contains(solution);
    }

    public boolean isSolved() {
        return submitted.contains(solution);
    }

    public MatchResult submit(Word guess) {
        requireNonNull(guess);
        if (submitted.contains(solution)){
            throw new IllegalStateException(String.format("Game is solved, no more guesses allowed. The solution is: %s", solution));
        }
        if (counter == 5) {
            throw new IllegalStateException(String.format("Game is finished, no more guesses allowed. Guess submitted: %s", guess));
        }
        if (!accepter.accept(guess)){
            throw new IllegalArgumentException(String.format("Word %s is not accepted", guess));
        }
        counter++;
        submitted.add(guess);
        return new MatchResult(List.of(Match.CORRECT, Match.CORRECT, Match.CORRECT, Match.CORRECT, Match.CORRECT));
    }

    public Collection<Word> getSubmitted() {
        return Collections.unmodifiableCollection(submitted);
    }
}
