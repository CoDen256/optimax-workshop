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

    private int counter = 0;
    private final Collection<Word> submitted = new ArrayList<>();

    public boolean isFinished() {
        return counter == 5;
    }

    public boolean isSolved() {
        return false;
    }

    public void submit(Word guess) {
        requireNonNull(guess);
        if (counter == 5) {
            throw new IllegalStateException(String.format("Game is finished, no more guesses allowed. Guess submitted: %s", guess));
        }
        counter++;
        submitted.add(guess);
    }

    public Collection<Word> getSubmitted() {
        return Collections.unmodifiableCollection(submitted);
    }
}
