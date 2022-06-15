package optimax.workshop.core.matcher;

public enum MatchType {
    ABSENT("x"), /* Indicates that a letter is absent in the expected word */
    WRONG("-"),  /* Indicates that a letter is in the expected word, but on the wrong position*/
    CORRECT("+"); /* Indicates that a letter is in the expected word, and on the same position*/

    private final String representation;

    MatchType(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}