package optimax.workshop.config.guesser;

import java.util.Collection;
import java.util.Scanner;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.run.guesser.Guesser;

/**
 * A {@link Guesser} that uses {@link System#in}, i.e. the user input
 * to produce valid guesses
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class StandardInputGuesser implements Guesser {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void init(Collection<Word> solutions, Collection<Word> accepted) {}

    @Override
    public Word nextGuess() {
        return new Word(readNextValidGuess());
    }

    private String readNextValidGuess() {
        boolean validGuess;
        String word;
        do {
            System.out.print("Your next guess:");
            word = scanner.nextLine();
            validGuess = Word.isValid(word);
            if (!validGuess) System.out.printf("Word %s is invalid%n", word);
        } while (!validGuess);
        return word;
    }

    @Override
    public void match(Word guess, MatchResult result) {
        /* yeah... whatever */
    }
}
