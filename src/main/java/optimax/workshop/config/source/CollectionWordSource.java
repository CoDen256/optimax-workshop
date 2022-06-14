package optimax.workshop.config.source;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import optimax.workshop.core.Word;
import optimax.workshop.core.dictionary.WordSource;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class CollectionWordSource implements WordSource {

    private final Collection<Word> dictionary;

    public CollectionWordSource(Collection<Word> dictionary) {
        this.dictionary = new HashSet<>(dictionary);
    }

    @Override
    public Collection<Word> getAll() {
        return Collections.unmodifiableCollection(dictionary);
    }
}
