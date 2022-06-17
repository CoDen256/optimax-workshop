package optimax.workshop.config.matcher;

import static java.util.function.Predicate.not;

import java.util.ArrayList;
import java.util.List;
import optimax.workshop.core.Letter;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.Match;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.core.matcher.MatchType;
import optimax.workshop.runner.WordMatcher;

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
        List<Letter> expected = expectedWord.letters();
        List<Letter> actual = actualWord.letters();
        List<Letter> alreadyMatched = new ArrayList<>();

        List<Match> matches = new ArrayList<>();
        actual.forEach(l ->
                matches.add(new Match(MatchType.ABSENT, l.getPos(), l.getChar()))
        );

        for (Letter actualLetter : actual) {
            if (expected.contains(actualLetter)) {
                alreadyMatched.add(actualLetter);
                matches.set(actualLetter.getPos(), new Match(MatchType.CORRECT, actualLetter.getPos(), actualLetter.getChar()));
            }
        }

        for (Letter actualLetter : actual) {
            if (alreadyMatched.contains(actualLetter)) continue;
            expected.stream()
                    .filter(not(alreadyMatched::contains))
                    .filter(expectedChar -> isAtWrongPosition(actualLetter, expectedChar))
                    .findFirst()
                    .ifPresent(match -> {
                        alreadyMatched.add(match);
                        matches.set(actualLetter.getPos(), new Match(MatchType.WRONG, actualLetter.getPos(), actualLetter.getChar()));
                    });
        }
        return new MatchResult(matches);
    }

    private boolean isAtWrongPosition(Letter a, Letter b) {
        return b.getChar() == a.getChar() && b.getPos() != a.getPos();
    }

}
