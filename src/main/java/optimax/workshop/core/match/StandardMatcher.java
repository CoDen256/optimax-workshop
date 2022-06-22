package optimax.workshop.core.match;

import static java.util.function.Predicate.not;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import optimax.workshop.core.Word;

/**
 * Represents the standard Wordle matcher, that computes a {@link MatchResult} by following rules:
 * Each letter of the actual word is compared with the letter in the expected word.
 * Based on the presence of other letters and their positions a {@link Match} for a certain position
 * is created in relation to the {@code actual} word.
 * <ul>
 *     <li>1. The letters is the same and on the same position : {@code CORRECT} </li>
 *     <li>2. The letter is not present in the expected word : {@code ABSENT} </li>
 *     <li>3. The letter is present in the expected word, but on wrong position : {@code WRONG} </li>
 * </ul>
 * <b>Note:</b> Multiple occurrences of the same letter(which exists only once in the expected word) do not produce
 * the same match. For example for expected word 'valid' and actual 'axaid' produces 'WRONG, ABSENT, <b>ABSENT</b>, CORRECT, CORRECT'
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class StandardMatcher implements WordMatcher {
    @Override
    public MatchResult match(Word expectedWord, Word actualWord) {
        List<Letter> expected = letters(expectedWord);
        List<Letter> actual = letters(actualWord);
        List<Letter> alreadyMatched = new ArrayList<>();
        List<Match> matches = new ArrayList<>();

        matchCorrectLetters(expected, actual, alreadyMatched, matches);
        matchWrongLetters(expected, actual, alreadyMatched, matches);
        matchAbsentLetters(actual, matches);

        return new MatchResult(matches);
    }

    private void matchCorrectLetters(List<? extends Letter> expected, List<? extends Letter> actual,
                                     List<? super Letter> alreadyMatched, List<? super Match> matches) {
        actual.stream()
                .filter(expected::contains)
                .forEach(actualLetter -> {
                    alreadyMatched.add(actualLetter);
                    matches.add(Match.correct(actualLetter.pos, actualLetter.letter));
                });
    }

    private void matchWrongLetters(List<? extends Letter> expected, List<? extends Letter> actual,
                                   List<? super Letter> alreadyMatched, List<? super Match> matches) {
        for (Letter actualLetter : actual) {
            if (alreadyMatched.contains(actualLetter)) continue;
            expected.stream()
                    .filter(not(alreadyMatched::contains))
                    .filter(expectedChar -> isAtWrongPosition(actualLetter, expectedChar))
                    .findFirst()
                    .ifPresent(match -> {
                        alreadyMatched.add(match);
                        matches.add(Match.wrong(actualLetter.pos, actualLetter.letter));
                    });
        }
    }

    private void matchAbsentLetters(List<? extends Letter> actual, List<Match> matches) {
        actual.stream()
                .filter(l -> notContains(matches, l))
                .forEach(l -> matches.add(Match.absent(l.pos, l.letter)));
    }

    /** Checks whether the letter was already matched */
    private boolean notContains(List<? extends Match> matches, Letter letter){
        return matches.stream().noneMatch(m -> m.getPos() == letter.pos && m.getLetter() == letter.letter);
    }

    /** Checks whether the letters are the same, but on the wrong position */
    private boolean isAtWrongPosition(Letter a, Letter b) {
        return b.letter == a.letter && b.pos != a.pos;
    }

    /**
     * Computes an ordered list of {@link Letter} of the given word
     *
     * @param word
     *         the original word
     * @return the list of containing letters
     */
    private List<Letter> letters(Word word) {
        return IntStream.range(0, 5)
                .mapToObj(i -> new Letter(word.letter(i), i))
                .collect(Collectors.toList());
    }

    /**
     * A helper class, that represents a letter with a corresponding position in the containing word
     */
    private static final class Letter {
        public final char letter;
        public final int pos;

        public Letter(char letter, int pos) {
            this.letter = letter;
            this.pos = pos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Letter that = (Letter) o;
            return letter == that.letter && pos == that.pos;
        }

        @Override
        public int hashCode() {
            return Objects.hash(letter, pos);
        }
    }
}
