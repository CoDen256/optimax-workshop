package optimax.workshop.core.matcher;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        verify(matches);
        this.matches = matches.stream().sorted(Comparator.comparing(Match::getPos)).collect(Collectors.toList());
    }

    private void verify(Collection<Match> matches) {
        requireNonNull(matches);
        if (matches.size() != 5) {
            throw new InvalidMatchResultException(String.format("Match result must contain exactly 5 matches, but was: %d", matches.size()));
        }
        IntStream.range(0, 5).forEach(i -> {
            Optional<Match> matchForPosition = matches.stream().filter(m -> m.getPos() == i).findAny();
            if (matchForPosition.isEmpty()) {
                throw new MissingMatchPositionException(String.format("Match for position %d not found", i));
            }
        });
    }

    public MatchType matchTypeAt(int pos) {
        checkBounds(pos);
        return matches.get(pos).getType();
    }

    public Match matchAt(int pos) {
        checkBounds(pos);
        return matches.get(pos);
    }

    public List<Match> getMatches(){
        return Collections.unmodifiableList(matches);
    }

    private void checkBounds(int pos) {
        if (!(pos >= 0 && pos < 5)) {
            throw new IndexOutOfBoundsException(String.format("Position of the match should be in range [0;4], but was: %d", pos));
        }
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

    public static final class InvalidMatchResultException extends RuntimeException {
        public InvalidMatchResultException(String message) {
            super(message);
        }

        public InvalidMatchResultException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static final class MissingMatchPositionException extends RuntimeException {
        public MissingMatchPositionException(String message) {
            super(message);
        }

        public MissingMatchPositionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}


