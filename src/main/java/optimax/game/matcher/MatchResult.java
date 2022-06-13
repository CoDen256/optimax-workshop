package optimax.game.matcher;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a {@code MatchResult}, containing 5 {@link Match}es for each letter in the {@link optimax.game.Word}
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class MatchResult {
    private final List<Match> matches;

    public MatchResult(Match... matches) {
        this(Arrays.asList(matches));
    }

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

    @Override
    public String toString() {
        return matches.stream()
                .map(m -> {
                    if (m == Match.ABSENT) return "x";
                    else if (m == Match.WRONG) return "-";
                    else if (m == Match.CORRECT) return "+";
                    else throw new AssertionError();
                })
                .collect(Collectors.joining("", "Result[", "]"));
    }
}


