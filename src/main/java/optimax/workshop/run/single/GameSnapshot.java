package optimax.workshop.run.single;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.guesser.Guesser;
import optimax.workshop.wordsource.WordAccepter;

/**
 * A {@code GameSnapshot} is a snapshot of a <b>single</b> game, that contains its current
 * state as well as corresponding metrics and scores
 */
public class GameSnapshot {
    /** The solution of the game */
    private final Word solution;
    /** Tells whether the game is solved of failed */
    private final boolean isSolved;
    /** The ordered <b>valid</b> guesses that were made */
    private final List<Word> guesses = new ArrayList<>();
    /** The ordered matches corresponding to the submitted guesses */
    private final List<MatchResult> matches = new ArrayList<>();
    /** Current game index */
    private final int gameIndex;
    /** The time game started */
    private final long startMillis;

    /** The time game ended */
    private final long endMillis;
    /** The {@link Guesser} being used in the game */
    private final Guesser guesser;
    /** The {@link WordAccepter} being used in the game */
    private final WordAccepter accepter;

    public GameSnapshot(Word solution, int gameIndex, long startMillis, Guesser guesser, WordAccepter accepter) {
        this.solution = solution;
        this.gameIndex = gameIndex;
        this.isSolved = false;
        this.guesser = guesser;
        this.accepter = accepter;
        this.endMillis = 0;
        this.startMillis = startMillis;
    }

    private GameSnapshot(Word solution, int gameIndex, Guesser guesser, WordAccepter accepter,
                         boolean isSolved, long startMillis, long endMillis,
                         List<Word> guesses, List<MatchResult> matches) {
        this.solution = solution;
        this.isSolved = isSolved;
        this.gameIndex = gameIndex;
        this.guesser = guesser;
        this.accepter = accepter;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
        this.guesses.addAll(guesses);
        this.matches.addAll(matches);
        verifySubmittedGuesses(guesses, matches);
    }

    private void verifySubmittedGuesses(List<Word> guesses, List<MatchResult> matches) {
        if (guesses.size() != matches.size()) {
            throw new IllegalArgumentException(format("Number of submit guesses must equal the number of matches:"
                    + " %d guesses != %d matches", guesses.size(), matches.size()));
        }
    }

    public Word getSolution() {
        return solution;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public List<Word> getGuesses() {
        return Collections.unmodifiableList(guesses);
    }

    public List<MatchResult> getMatches() {
        return Collections.unmodifiableList(matches);
    }

    public Word getLastGuess() {
        return guesses.get(guesses.size() - 1);
    }

    public long getDurationMillis() {
        return endMillis-startMillis;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    public MatchResult getLastMatch() {
        return matches.get(matches.size() - 1);
    }

    public int getIndex() {
        return gameIndex;
    }

    public int getGuessesCount() {
        return guesses.size();
    }

    public Guesser getGuesser() {
        return guesser;
    }

    public WordAccepter getAccepter() {
        return accepter;
    }

    public GameSnapshot addSubmission(Word guess, MatchResult match) {
        List<MatchResult> newMatches = new ArrayList<>(getMatches());
        newMatches.add(match);
        List<Word> newGuesses = new ArrayList<>(getGuesses());
        newGuesses.add(guess);
        return new GameSnapshot(solution, gameIndex, guesser,
                accepter, isSolved,
                startMillis, endMillis,
                newGuesses, newMatches);
    }

    public GameSnapshot setSolved(boolean isSolved, long endMillis) {
        return new GameSnapshot(solution, gameIndex, guesser, accepter, isSolved, startMillis, endMillis, guesses, matches);
    }
}
