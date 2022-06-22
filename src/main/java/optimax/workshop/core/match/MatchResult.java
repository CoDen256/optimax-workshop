package optimax.workshop.core.match;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

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
 * All the matches are sorted by their appearance in the actual word.
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class MatchResult {
    /** The matches for each letter ordered by its position in the word */
    private final List<Match> matches;

    public MatchResult(Collection<Match> matches) {
        verify(matches);
        this.matches = matches.stream().sorted(Comparator.comparing(Match::getPos)).collect(toList());
    }

    private void verify(Collection<Match> matches) {
        requireNonNull(matches);
        if (matches.size() != 5) {
            throw new InvalidMatchResultException(String.format("Match result must contain exactly 5 matches, but was: %d", matches.size()));
        }
        IntStream.range(0, 5).forEach(i -> matches.stream()
                .filter(m -> m.getPos() == i)
                .findAny()
                .orElseThrow(() -> new MissingMatchPositionException(String.format("Match for position %d not found", i))));
    }

    /**
     * Returns a {@link MatchType} of a {@link Match} at position {@code pos}.
     *
     * @param pos
     *         the position of the corresponding letter
     * @return the type of the match (can be {@link MatchType#ABSENT}, {@link MatchType#WRONG}, or {@link MatchType#CORRECT})
     */
    public MatchType matchTypeAt(int pos) {
        checkBounds(pos);
        return matches.get(pos).getType();
    }

    /**
     * Returns a {@link Match} for the letter at position {@code pos}.
     *
     * @param pos
     *         the position of the matched letter
     * @return a {@link Match} containing position, type and the letter that was matched
     */
    public Match matchAt(int pos) {
        checkBounds(pos);
        return matches.get(pos);
    }

    /**
     * Get all matches ordered by their position in the corresponding {@link Word}.
     *
     * @return the ordered list of all matches
     * @see Match
     */
    public List<Match> getMatches() {
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

    /** Represents an exception for invalid match result or invalid number of matches */
    public static final class InvalidMatchResultException extends RuntimeException {
        public InvalidMatchResultException(String message) {
            super(message);
        }

        public InvalidMatchResultException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /** This exception is thrown, if the match result have one or more missing matches */
    public static final class MissingMatchPositionException extends RuntimeException {
        public MissingMatchPositionException(String message) {
            super(message);
        }

        public MissingMatchPositionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}


