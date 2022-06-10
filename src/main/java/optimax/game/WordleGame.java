package optimax.game;

import static java.util.Objects.requireNonNull;
import static optimax.game.Match.ABSENT;
import static optimax.game.Match.CORRECT;
import static optimax.game.Match.WRONG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import optimax.game.accepter.WordAccepter;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public final class WordleGame {

    private final Word solution;
    private final Collection<Word> submitted = new ArrayList<>();
    private final WordAccepter accepter;

    public WordleGame(Word solution, WordAccepter accepter) {
        this.accepter = requireNonNull(accepter);
        this.solution = requireNonNull(solution);
        if (!accepter.accept(solution)){
            throw new IllegalArgumentException(String.format("Solution (%s) is not accepted", solution));
        }
    }

    public boolean isFinished() {
        return submitted.size() == 5 || isSolved();
    }

    public boolean isSolved() {
        return submitted.contains(solution);
    }

    public MatchResult submit(Word guess) {
        requireNonNull(guess);
        if (isSolved())
            throw new IllegalStateException(String.format("Game is solved, no more guesses allowed. The solution is: %s", solution));
        if (isFinished())
            throw new IllegalStateException(String.format("Game is finished, no more guesses allowed. Guess submitted: %s", guess));
        if (!accepter.accept(guess))
            throw new IllegalArgumentException(String.format("Word %s is not accepted", guess));
        submitted.add(guess);
        return match(guess, solution);
    }

    private MatchResult match(Word guess, Word solution){
        List<String> solutionChars = new ArrayList<>(Arrays.asList(solution.word().split("")));
        List<String> guessChars =  new ArrayList<>(Arrays.asList(guess.word().split("")));

        String matched = "matched";
        String wronged = "wronged";
        Match[] matches = new Match[]{CORRECT, CORRECT, CORRECT, CORRECT, CORRECT};
        for (int i = 0; i < 5; i++) {
            if (solutionChars.get(i).equals(guessChars.get(i))){
                solutionChars.set(i, matched);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (solutionChars.get(i).equals(matched)) continue;

            int indx = solutionChars.indexOf(guessChars.get(i));
            if (!solutionChars.get(i).equals(guessChars.get(i)) && indx != -1) {
                solutionChars.set(indx, wronged);
                matches[i] = WRONG;
            }
            else if (!solutionChars.get(i).equals(guessChars.get(i))){
                matches[i] = ABSENT;
            }
        }
        return new MatchResult(matches);
    }
    public Collection<Word> getSubmitted() {
        return Collections.unmodifiableCollection(submitted);
    }
}
