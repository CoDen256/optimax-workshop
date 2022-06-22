package optimax.workshop.core.match;

/**
 * Represents a type of the match for a particular letter,
 * i.e. YELLOW, BLACK or GREEN match in the Wordle Game
 */
public enum MatchType {
    /** Black: Indicates that a letter is absent in the expected word */
    ABSENT("x"),
    /** Yellow: Indicates that a letter is in the expected word, but on the wrong position */
    WRONG("-"),
    /** Green: Indicates that a letter is in the expected word, and on the same position */
    CORRECT("+");

    /** Internal representation */
    private final String toString;

    MatchType(String toString) {
        this.toString = toString;
    }

    @Override
    public String toString() {
        return toString;
    }
}