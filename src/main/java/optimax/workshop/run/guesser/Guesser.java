package optimax.workshop.run.guesser;

import java.util.Collection;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.Match;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.core.match.MatchType;

/**
 * The {@code Guesser} interface represents a strategy for making guesses in the Wordle Game
 * based on an initial solution set, accepted set and multiple feedbacks from previous guesses.
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public interface Guesser {
    /**
     * At the start of the game the {@code Guesser} is provided with two collections of {@link Word}s: <br>
     * - the solution set i.e. the set that is being used for generating the target words (solutions) <br>
     * - the accepted set i.e. the set that is being used to check whether the guess is accepted. <br>
     * <p>
     * The {@code Guesser} may provide guesses from accepted set that are not a part of the solution set,
     * but will nevertheless help it in narrowing down the possible solution set. <br>
     * Thus, the accepted set and the solution set may differ, but the solution set is always a subset of the accepted set.
     *
     * @param solutions
     *         based on solution set the solutions will be generated
     * @param accepted
     *         based on accepted set the guesses will be verified
     */
    void init(Collection<Word> solutions, Collection<Word> accepted);

    /**
     * This method is called at the start of each guess to provide the next guess, that the strategy generates.
     * A guess is an instance of {@link Word}, which represents a valid 5 letter Wordle Word.
     * <p>
     * A {@link Word} can be created only from valid input {@code String}, which has the following properties: <br>
     * - It is CASE INSENSITIVE, both Word("ABCDE") and Word("abcde") are valid equal words <br>
     * - Contains only alphabetic characters <br>
     * - Contains exactly 5 characters <br>
     * - The order of the letters retains <br>
     * - is not {@code null}
     * To check whether the {@link Word} can be created from the string, use {@link Word#isValid(String)}
     * <p>
     * If the guess is rejected, meaning the guess is not an element of the accepted set, then the {@link Guesser#match(Word, MatchResult)}
     * won't be called and instead either this method will be called again or an exception will be thrown (depending on the
     * game runner settings).
     *
     * @return a valid guess {@link Word}
     */
    Word nextGuess();

    /**
     * Once a valid guess {@link Word} via {@link Guesser#nextGuess()} is submitted, it will be compared with the solution {@link Word}
     * and a {@link MatchResult} will be generated, containing 5 {@link Match}es for each letter in the guess word. <br>
     * The corresponding {@link MatchResult} together with the submitted guess is passed back to the {@code Guesser} via
     * this method to provide the feedback on the previous submissions.
     * <p>
     * A match result contains 5 {@link Match}es. Each {@link Match} contains
     * - a letter of the submitted word,<br>
     * - a position of the letter in the submitted word <br>
     * - a {@link MatchType} specifying the type of the match (i.e.
     * {@link MatchType#ABSENT} (Black),
     * {@link MatchType#WRONG} (Yellow),
     * {@link MatchType#CORRECT} (Green))
     *
     * @param guess
     *         the previous guess, to which the result is referring
     * @param result
     *         the product of comparison of the previously submitted guess with the solution {@link Word}.
     *         It contains 5 {@link Match}es for each letter of the submitted guess.
     */
    void match(Word guess, MatchResult result);


    /**
     * Returns a human-readable name for the guesser to distinguish it from other guessers.
     * (E.g. will be used when printing the stats of the guesser)
     *
     * @return the name of the guesser
     */
    default String name() {
        return getClass().getSimpleName();
    }
}
