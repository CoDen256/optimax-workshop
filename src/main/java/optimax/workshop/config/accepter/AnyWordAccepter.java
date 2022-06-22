package optimax.workshop.config.accepter;

import optimax.workshop.core.Word;
import optimax.workshop.wordsource.WordAccepter;

/**
 * A {@link WordAccepter} that accepts any given word
 */
public class AnyWordAccepter implements WordAccepter {
    @Override
    public boolean isAccepted(Word word) {
        return true;
    }
}
