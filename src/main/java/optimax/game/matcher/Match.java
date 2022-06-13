package optimax.game.matcher;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public enum Match {
    ABSENT, /* Indicates that a letter is absent in the expected word */
    WRONG,  /* Indicates that a letter is in the expected word, but on the wrong position*/
    CORRECT /* Indicates that a letter is in the expected word, and on the same position*/

}
