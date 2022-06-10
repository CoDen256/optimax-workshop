package optimax.game.accepter;

import optimax.game.Word;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface WordAccepter {
    boolean accept(Word word);
}
