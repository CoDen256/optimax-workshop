package optimax.game.accepter;

import optimax.game.Word;

/**
 * A {@code WordAccepter} tells whether the given word will be accepted
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
@FunctionalInterface
public interface WordAccepter {
    boolean isAccepted(Word word);

    default boolean isNotAccepted(Word word) {
        return !isAccepted(word);
    }
}
