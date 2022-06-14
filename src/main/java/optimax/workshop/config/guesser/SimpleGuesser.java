package optimax.workshop.config.guesser;

import optimax.workshop.core.Word;
import optimax.workshop.core.dictionary.WordAccepter;
import optimax.workshop.core.dictionary.WordSource;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.Guesser;

public class SimpleGuesser implements Guesser {


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
}
