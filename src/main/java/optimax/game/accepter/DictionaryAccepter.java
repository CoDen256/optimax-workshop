package optimax.game.accepter;

import java.util.Collection;
import optimax.game.Word;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class DictionaryAccepter implements WordAccepter {
    public DictionaryAccepter(Collection<Word> source) {
    }

    @Override
    public boolean accept(Word word) {
        return false;
    }
}
