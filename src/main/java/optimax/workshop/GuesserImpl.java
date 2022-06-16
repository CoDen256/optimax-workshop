package optimax.workshop;

import java.util.Collection;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.Guesser;

public class GuesserImpl implements Guesser {

    @Override
    public void init(Collection<Word> solutions, Collection<Word> accepted) {

    }

    @Override
    public Word nextGuess() {
        return null;
    }

    @Override
    public void match(Word guess, MatchResult result) {

    }

    private static boolean isWordValid(String word){
        return Word.isValid(word);
    }
}
