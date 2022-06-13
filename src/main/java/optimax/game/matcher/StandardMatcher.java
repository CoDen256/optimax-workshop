package optimax.game.matcher;

import static optimax.game.matcher.Match.ABSENT;
import static optimax.game.matcher.Match.CORRECT;
import static optimax.game.matcher.Match.WRONG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * @author Denys Chernyshov
 * @since 1.0
 */
public class StandardMatcher implements WordMatcher {
    @Override
    public MatchResult match(Word expected, Word actual) {
        List<String> solutionChars = new ArrayList<>(Arrays.asList(expected.word().split("")));
        List<String> guessChars =  new ArrayList<>(Arrays.asList(actual.word().split("")));

        String matched = "matched";
        String wronged = "wronged";
        Match[] matches = new Match[]{CORRECT, CORRECT, CORRECT, CORRECT, CORRECT};
        for (int i = 0; i < 5; i++) {
            if (solutionChars.get(i).equals(guessChars.get(i))){
                solutionChars.set(i, matched);
            }
        }

        for (int i = 0; i < 5; i++) {
            String target = solutionChars.get(i);
            String guess = guessChars.get(i);
            if (target.equals(matched)) continue;

            int indx = solutionChars.indexOf(guess);
            if (!target.equals(guess) && indx != -1) {
                solutionChars.set(indx, wronged);
                matches[i] = WRONG;
            }
            else if (!target.equals(guess)){
                matches[i] = ABSENT;
            }
        }
        return new MatchResult(matches);
    }
}
