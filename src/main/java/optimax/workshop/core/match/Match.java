package optimax.workshop.core.match;

import java.util.Objects;
import optimax.workshop.core.Word;

/**
 * Represents a match of a letter in the actual {@link Word} to the expected {@link Word}
 * 5 Matches, each corresponding to a letter of a word builds a full {@link MatchResult}
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class Match {

    /** Type of a match (e.g. {@link MatchType#WRONG} (Yellow),
     * {@link MatchType#ABSENT}(Black),
     * {@link MatchType#CORRECT}(Green)
     * */
    private final MatchType type;
    /** The position of the match in the actual word */
    private final int pos;
    /** The letter of the actual word, that was compared */
    private final char letter;

    public Match(MatchType type, int pos, char letter) {
        this.type = type;
        this.pos = pos;
        this.letter = letter;
    }

    public MatchType getType() {
        return type;
    }

    public int getPos() {
        return pos;
    }

    public char getLetter() {
        return letter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return pos == match.pos && type == match.type && letter == match.letter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, pos, letter);
    }

    @Override
    public String toString() {
        return String.format("(%s:%d:%s)", type.name(), pos, letter);
    }

    /** Creates a {@link Match} of type {@link MatchType#CORRECT} by the given position and letter */

    public static Match correct(int pos, char letter) {
        return new Match(MatchType.CORRECT, pos, letter);
    }

    /** Creates a {@link Match} of type {@link MatchType#WRONG} by the given position and letter */

    public static Match wrong(int pos, char letter) {
        return new Match(MatchType.WRONG, pos, letter);
    }

    /** Creates a {@link Match} of type {@link MatchType#ABSENT} by the given position and letter */
    public static Match absent(int pos, char letter) {
        return new Match(MatchType.ABSENT, pos, letter);
    }
}
