package optimax.workshop.config.guesser;

import static optimax.workshop.core.matcher.MatchType.ABSENT;
import static optimax.workshop.core.matcher.MatchType.CORRECT;
import static optimax.workshop.core.matcher.MatchType.WRONG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import optimax.workshop.core.Letter;
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
    private Set<Match> matches = new HashSet<>();

    private Word fallback;

    @Override
    public void init(WordSource source, WordAccepter accepter) {
        words = new ArrayList<>(source.getAll());
        this.accepter = accepter;
        fallback = words.get(0);
    }

    @Override
    public Word nextGuess() {
        List<Word> guesses = this.words.stream().filter(matchesCriteria()).collect(Collectors.toList());
        return guesses.isEmpty() ? fallback : guesses.get(0);
    }

    @Override
    public void match(Word guess, MatchResult result) {
        submitted.add(guess);
        matches.addAll(result.getMatches());
    }

    private Predicate<Word> matchesCriteria(){
        return word -> {
            for (Match match : matches) {
                char letter = match.getLetter();
                int pos = match.getPos();
                if (match.getType() == ABSENT && wordContainsLetter(word, letter)){
                    return false;
                }

                if (match.getType() == CORRECT && word.letter(pos) != letter){
                    return false;
                }

                if (match.getType() == WRONG && !(wordContainsLetter(word, letter) && word.letter(pos) != letter)){
                    return false;
                }
            }
            return true;
        };
    }

    private boolean wordContainsLetter(Word word, char letter) {
        return word.letters().stream().map(Letter::getChar).anyMatch(c -> c == letter);
    }
}
