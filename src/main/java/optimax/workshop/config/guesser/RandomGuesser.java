package optimax.workshop.config.guesser;

import java.util.List;
import java.util.Random;
import optimax.workshop.core.Word;
import optimax.workshop.core.dictionary.WordAccepter;
import optimax.workshop.core.dictionary.WordSource;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.Guesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RandomGuesser implements Guesser {

    private List<Word> dictionary;


    @Override
    public void init(WordSource source, WordAccepter accepter) {

    }

    @Override
    public Word nextGuess() {
        Random rnd = new Random();
        int i = rnd.nextInt(dictionary.size());
        return dictionary.get(1);
    }

    @Override
    public void match(Word guess, MatchResult result) {

    }
}
