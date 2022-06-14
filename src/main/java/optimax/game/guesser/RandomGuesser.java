package optimax.game.guesser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import optimax.game.core.Word;
import optimax.game.core.matcher.MatchResult;
import optimax.game.run.guesser.DictionaryAwareGuesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RandomGuesser implements DictionaryAwareGuesser {

    private List<Word> dictionary;

    @Override
    public void init(Collection<Word> dictionary) {
        this.dictionary = new ArrayList<>(dictionary);
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

    @Override
    public void finish() {

    }
}
