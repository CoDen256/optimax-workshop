package optimax.workshop.config.guesser;

import static optimax.workshop.core.matcher.MatchType.ABSENT;
import static optimax.workshop.core.matcher.MatchType.CORRECT;
import static optimax.workshop.core.matcher.MatchType.WRONG;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.Match;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.WordAccepter;
import optimax.workshop.runner.WordSource;

public class SimpleGuesser implements Guesser {

    private List<Word> words;
    private List<Word> submitted = new ArrayList<>();
    private WordAccepter accepter;
    private List<Match> matches = new ArrayList<>();

    private Word fallback;

    @Override
    public void init(WordSource source, WordAccepter accepter) {
        words = new ArrayList<>(source.getAll());
        this.accepter = accepter;
        fallback = words.get(0);
    }

    @Override
    public Word nextGuess() {
        words.removeIf(Predicate.not(filterMatches()));
        return words.isEmpty() ? fallback : words.get(0);
    }

    @Override
    public void match(Word guess, MatchResult result) {
        submitted.add(guess);
        matches.addAll(result.getMatches());
    }

    private Predicate<Word> filterMatches(){
        return word -> {
            for (Match match : matches) {
                if (match.getType() == ABSENT){
                    if (word.word().contains(Character.toString(match.getLetter()))){
                        return false;
                    }
                }

                if (match.getType() == CORRECT){
                    if (word.word().charAt(match.getPos()) != match.getLetter()){
                        return false;
                    }
                }

                if (match.getType() == WRONG){
                    if (!word.word().contains(Character.toString(match.getLetter())) ||
                            word.letter(match.getPos()) == match.getLetter()
                    ){
                        return false;
                    }
                }
            }
            return true;
        };
    }
}
