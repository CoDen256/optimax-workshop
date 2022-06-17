package optimax.workshop.config.guesser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.Match;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.core.matcher.MatchType;
import optimax.workshop.runner.Guesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RegexBasedGuesser implements Guesser {

    public static final String MATCH_ANY_LETTER_REGEX = "[ABCDEFGHIJKLMNOPQRSTUVWXYZ]";

    private final List<String> regexList = initializeRegex();

    private final List<Character> presentLetters = new ArrayList<>();

    private final List<String> wordList = new ArrayList<>();

    private final Random random = new Random();

    @Override
    public void init(Collection<Word> solutions, Collection<Word> accepted) {
        wordList.addAll(solutions.stream().map(Word::word).collect(Collectors.toList()));
    }

    @Override
    public Word nextGuess() {
        return getMatches()
                .limit(10)
//                .peek(System.out::println)
                .findFirst()
                .map(Word::new)
                .orElse(fallback());
    }

    @Override
    public void match(Word guess, MatchResult result) {
        updateRegex(result);
    }

    private Word fallback() {
        return new Word(getRandomWord(wordList));
    }

    private <T> T getRandomWord(List<T> collection) {
        return collection.get(random.nextInt(collection.size()));
    }


    private Stream<String> getMatches() {
        String regex = String.join("", regexList);
        return wordList.stream().filter(w -> w.matches(regex) && containsAllPresentLetters(w));
    }

    private boolean containsAllPresentLetters(String currentWord) {
        return presentLetters.stream().allMatch(l -> currentWord.contains(l.toString()));
    }

    private void updateRegex(MatchResult feedback) {
        for (Match match : feedback.getMatches()) {
            int pos = match.getPos();
            char currentChar = match.getLetter();
            MatchType matchForChar = match.getType();

            if (matchForChar == MatchType.ABSENT) {
                updateRegexWithAbsent(pos, currentChar);
            } else if (matchForChar == MatchType.WRONG) {
                updateRegexWithWrong(pos, currentChar);
            } else if (matchForChar == MatchType.CORRECT) {
                updateRegexWithCorrect(pos, currentChar);
            }
        }
    }

    private void updateRegexWithCorrect(int pos, char currentChar) {
        updateRegexForLetterAtPos(pos, s -> String.valueOf(currentChar));
        presentLetters.add(currentChar);
    }

    private void updateRegexWithWrong(int pos, char currentChar) {
        updateRegexForLetterAtPos(pos, s -> removeChar(s, currentChar));
        presentLetters.add(currentChar);
    }

    private void updateRegexWithAbsent(int pos, char currentChar) {
        if (presentLetters.contains(currentChar)) {
            updateRegexForLetterAtPos(pos, s -> removeChar(s, currentChar));
        } else {
            for (int regexIndex = 0; regexIndex < 5; regexIndex++) {
                updateRegexForLetterAtPos(regexIndex, s -> removeChar(s, currentChar));
            }
        }
    }

    private String removeChar(String origin, char c){
        return origin.replace(String.valueOf(c), "");
    }
    private void updateRegexForLetterAtPos(int pos, UnaryOperator<String> regexUpdater){
        regexList.set(pos, regexUpdater.apply(regexList.get(pos)));
    }

    private List<String> initializeRegex() {
        return Stream
                .generate(MATCH_ANY_LETTER_REGEX::toLowerCase)
                .limit(5)
                .collect(Collectors.toList());
    }
}
