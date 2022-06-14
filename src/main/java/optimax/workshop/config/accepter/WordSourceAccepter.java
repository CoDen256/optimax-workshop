package optimax.workshop.config.accepter;

import java.util.Collection;
import java.util.HashSet;
import optimax.workshop.core.Word;
import optimax.workshop.core.dictionary.WordAccepter;
import optimax.workshop.core.dictionary.WordSource;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordSourceAccepter implements WordAccepter {
    private final Collection<Word> source;

    public WordSourceAccepter(WordSource source) {
        this.source = new HashSet<>(source.getAll());
    }
    @Override
    public boolean isAccepted(Word word) {
        return source.contains(word);
    }
}
