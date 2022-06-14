package optimax.game.run.guesser;

import java.util.Collection;
import optimax.game.core.Word;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface DictionaryAwareGuesser extends Guesser {
    void init(Collection<Word> dictionary);
}
