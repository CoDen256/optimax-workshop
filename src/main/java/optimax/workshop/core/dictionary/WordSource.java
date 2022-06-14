package optimax.workshop.core.dictionary;

import java.util.Collection;
import optimax.workshop.core.Word;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface WordSource{
    Collection<Word> getAll();
}
