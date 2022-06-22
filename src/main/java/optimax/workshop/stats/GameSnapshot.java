package optimax.workshop.stats;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.run.guesser.Guesser;
import optimax.workshop.run.words.WordAccepter;

public class GameSnapshot {

    private final Word solution;

    private final boolean isSolved;

    private final List<Word> guesses = new ArrayList<>();

    private final List<MatchResult> matches = new ArrayList<>();

    private final int gameIndex;

    private final long millis;
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

    public Word getLastGuess(){
        return guesses.get(guesses.size()-1);
    }

    public long getMillis() {
        return millis;
    }

    public MatchResult getLastMatch(){
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
        return new GameSnapshot(solution, gameIndex, guesser, accepter, isSolved,millis, guesses, matches);
    }
}
