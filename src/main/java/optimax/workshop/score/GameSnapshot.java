package optimax.workshop.score;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.run.guesser.Guesser;
import optimax.workshop.run.words.WordAccepter;

/**
 * A {@code GameSnapshot} is a snapshot of a single game, that contains its current
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
    /** The total game time */
    private final long millis;
    /**  */
    private final Guesser guesser;
    private final WordAccepter accepter;

    public GameSnapshot(Word solution, int gameIndex, Guesser guesser, WordAccepter accepter) {
        this.solution = solution;
        this.gameIndex = gameIndex;
        this.isSolved = false;
        this.guesser = guesser;
        this.accepter = accepter;
        this.millis = 0;
    }

    private GameSnapshot(Word solution, int gameIndex, Guesser guesser, WordAccepter accepter,
                         boolean isSolved, long millis, List<Word> guesses, List<MatchResult> matches) {
        this.solution = solution;
        this.isSolved = isSolved;
        this.gameIndex = gameIndex;
        this.guesser = guesser;
        this.accepter = accepter;
        this.millis = millis;
        this.guesses.addAll(guesses);
        this.matches.addAll(matches);
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

    public long getMillis() {
        return millis;
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
        return new GameSnapshot(solution, gameIndex, guesser, accepter, isSolved, millis, newGuesses, newMatches);
    }

    public GameSnapshot setSolved(boolean isSolved, long millis) {
        return new GameSnapshot(solution, gameIndex, guesser, accepter, isSolved, millis, guesses, matches);
    }
}
