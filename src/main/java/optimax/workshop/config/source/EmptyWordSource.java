package optimax.workshop.config.source;

import java.util.Collection;
import java.util.Collections;
import optimax.workshop.core.Word;
import optimax.workshop.runner.WordSource;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class EmptyWordSource implements WordSource {
    @Override
    public Collection<Word> getAll() {
        return Collections.emptyList();
    }
}
