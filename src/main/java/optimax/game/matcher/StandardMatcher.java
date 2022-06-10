package optimax.game.matcher;

import static optimax.game.matcher.Match.ABSENT;
import static optimax.game.matcher.Match.CORRECT;
import static optimax.game.matcher.Match.WRONG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import optimax.game.Word;

/**
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
