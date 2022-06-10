package optimax.game.accepter;

import optimax.game.Word;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
@FunctionalInterface
public interface WordAccepter {
    boolean accept(Word word);

    default boolean isNotAccepted(Word word){
        return !accept(word);
    }
}
