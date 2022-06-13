package optimax.game.accepter;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import optimax.game.Word;

/**
 * Represents a {@link WordAccepter} that accepts only the words from the given dictionary (a set of words)
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class DictionaryAccepter implements WordAccepter {
    private final Collection<Word> dictionary;

    public DictionaryAccepter(Collection<Word> dictionary) {
        this.dictionary = new HashSet<>(requireNonNull(dictionary));
    }

    @Override
    public boolean isAccepted(Word word) {
        return dictionary.contains(word);
    }
}
