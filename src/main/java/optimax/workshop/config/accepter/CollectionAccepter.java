package optimax.workshop.config.accepter;

import java.util.Collection;
import java.util.HashSet;
import optimax.workshop.core.Word;
import optimax.workshop.wordsource.WordAccepter;

/**
 * A {@link WordAccepter} that accepts only the words from the given collection
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class CollectionAccepter implements WordAccepter {
    private final Collection<Word> source;

    public CollectionAccepter(Collection<Word> source) {
        this.source = new HashSet<>(source);
    }

    @Override
    public boolean isAccepted(Word word) {
        return source.contains(word);
    }
}
