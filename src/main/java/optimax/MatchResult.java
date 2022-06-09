package optimax;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class MatchResult {

    private final List<Match> matches;

    public MatchResult(List<Match> matches) {
        requireNonNull(matches);
        if (matches.size() != 5) {
            throw new IllegalArgumentException(String.format("Match result must contain exactly 5 matches, but was: %d", matches.size()));
        }
        this.matches = matches;
    }

    public Match getMatch(int pos) {
        if (!(pos >= 0 && pos < 5)) {
            throw new IllegalArgumentException(String.format("Position of the match should be from in range [0;4], but was: %d", pos));
        }
        return matches.get(pos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchResult result = (MatchResult) o;
        return matches.equals(result.matches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matches);
    }
}


