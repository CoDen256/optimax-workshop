package optimax.game.guesser;

import java.util.Collection;
import java.util.Scanner;
import optimax.game.core.Word;
import optimax.game.core.matcher.MatchResult;
import optimax.game.run.guesser.DictionaryAwareGuesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class ConsoleGuesser implements DictionaryAwareGuesser {

    private final Scanner scanner;
    private Collection<Word> dictionary;

    public ConsoleGuesser() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void init(Collection<Word> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public Word nextGuess() {
        System.out.print("Your next guess:");
        String word = scanner.nextLine();
        while (!Word.isValid(word) || !dictionary.contains(new Word(word))){
            System.out.print("Your next guess:");
            word = scanner.nextLine();
        }
        return new Word(word);
    }

    @Override
    public void match(Word guess, MatchResult result) {

    }

    @Override
    public void finish() {

    }
}
