package optimax.workshop.config.guesser;

import java.util.Scanner;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.WordAccepter;
import optimax.workshop.runner.WordSource;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class UserInputGuesser implements Guesser {

    private final Scanner scanner;
    private WordSource source;
    private WordAccepter accepter;

    public UserInputGuesser() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void init(WordSource source, WordAccepter accepter) {
        this.accepter = accepter;
    }

    @Override
    public Word nextGuess() {
        boolean validGuess = false;
        String word;
        do {
            System.out.print("Your next guess:");
            word = scanner.nextLine();
            if (!Word.isValid(word)){
                System.out.printf("Word %s is invalid%n", word);
            }
//            else if (accepter.isNotAccepted(new Word(word))){
//                System.out.printf("Word %s is not accepted%n", word);
//            }
            else {
                validGuess = true;
            }
        } while (!validGuess);
        return new Word(word);
    }

    @Override
    public void match(Word guess, MatchResult result) {

    }
}
