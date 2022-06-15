package optimax.workshop.core.matcher;

import java.util.Objects;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class Match {

    private final MatchType type;
    private final int pos;
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


}
