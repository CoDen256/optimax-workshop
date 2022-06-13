package optimax.game.matcher;

import static java.util.function.Predicate.not;
import static optimax.game.matcher.Match.ABSENT;
import static optimax.game.matcher.Match.CORRECT;
import static optimax.game.matcher.Match.WRONG;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import optimax.game.Word;

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
        List<WordCharacter> expected = indexed(expectedWord.word());
        List<WordCharacter> actual = indexed(actualWord.word());
        List<WordCharacter> alreadyMatched = new ArrayList<>();

        Match[] matches = new Match[]{ABSENT, ABSENT, ABSENT, ABSENT, ABSENT};

        for (WordCharacter actualChar : actual) {
            if (expected.contains(actualChar)) {
                alreadyMatched.add(actualChar);
                matches[actualChar.pos] = CORRECT;
            }
        }

        for (WordCharacter actualChar : actual) {
            if (alreadyMatched.contains(actualChar)) continue;
            expected.stream()
                    .filter(not(alreadyMatched::contains))
                    .filter(expectedChar -> isAtWrongPosition(actualChar, expectedChar))
                    .findFirst()
                    .ifPresent(match -> {
                        alreadyMatched.add(match);
                        matches[actualChar.pos] = WRONG;
                    });
        }

        return new MatchResult(matches);
    }

    private boolean isAtWrongPosition(WordCharacter a, WordCharacter b) {
        return b.character == a.character && b.pos != a.pos;
    }

    private List<WordCharacter> indexed(String word) {
        return IntStream.range(0, word.length())
                .boxed()
                .map(i -> new WordCharacter(word.charAt(i), i))
                .collect(Collectors.toList());
    }

    private static class WordCharacter {

        private final char character;
        private final int pos;

        public WordCharacter(char character, int pos) {
            this.character = character;
            this.pos = pos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WordCharacter that = (WordCharacter) o;
            return character == that.character && pos == that.pos;
        }

        @Override
        public int hashCode() {
            return Objects.hash(character, pos);
        }
    }
}
