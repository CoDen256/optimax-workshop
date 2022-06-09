package optimax;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public final class WordleGame {

    private final Word solution;
    private int counter = 0;
    private final Collection<Word> submitted = new ArrayList<>();
    private final Collection<? extends Word> dictionary;

    public WordleGame(Word solution, Collection<? extends Word> dictionary) {
        this.dictionary = requireNonNull(dictionary);
        this.solution = requireNonNull(solution);
        if (!dictionary.contains(solution)){
            throw new IllegalArgumentException(String.format("Solution (%s) is not in dictionary", solution));
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
        if (!dictionary.contains(guess)){
            throw new IllegalArgumentException(String.format("Word %s is not present in the dictionary", guess));
        }
        counter++;
        submitted.add(guess);
        return null;
    }

    public Collection<Word> getSubmitted() {
        return Collections.unmodifiableCollection(submitted);
    }
}
