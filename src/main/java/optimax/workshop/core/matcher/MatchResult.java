package optimax.workshop.core.matcher;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import optimax.workshop.core.Word;

/**
 * Represents a {@code MatchResult}, containing 5 {@link Match}es for each letter in the {@link Word}
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class MatchResult {
    private final List<Match> matches;

    public MatchResult(Collection<Match> matches) {
        requireNonNull(matches);
        if (matches.size() != 5) {
            throw new IllegalArgumentException(String.format("Match result must contain exactly 5 matches, but was: %d", matches.size()));
        }
        this.matches = matches.stream().sorted(Comparator.comparing(Match::getPos)).collect(Collectors.toList());
    }

    public MatchType matchTypeAt(int pos) {
        if (!(pos >= 0 && pos < 5)) {
            throw new IllegalArgumentException(String.format("Position of the match should be in range [0;4], but was: %d", pos));
        }
        return matches.get(pos).getType();
    }

    public List<Match> getMatches(){
        return Collections.unmodifiableList(matches);
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
                .map(m -> m.getType().toString())
                .collect(Collectors.joining("", "[", "]"));
    }
}


