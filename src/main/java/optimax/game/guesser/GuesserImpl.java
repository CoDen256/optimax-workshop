package optimax.game.guesser;

import java.util.Collection;
import optimax.game.core.Word;
import optimax.game.core.matcher.MatchResult;
import optimax.game.run.guesser.DictionaryAwareGuesser;

public class GuesserImpl implements DictionaryAwareGuesser {
    @Override
    public void init(Collection<Word> dictionary) {

    }
    @Override
    public Word nextGuess() {
        return null;
    }

    @Override
    public void match(Word guess, MatchResult result) {

    }

    @Override
    public void finish() {

    }
}
