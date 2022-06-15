package optimax.workshop;

import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.WordAccepter;
import optimax.workshop.runner.WordSource;

public class GuesserImpl implements Guesser {

    @Override
    public void init(WordSource source, WordAccepter accepter) {

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
