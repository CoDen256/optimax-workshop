package optimax.workshop.config.guesser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import optimax.workshop.core.match.Match;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.core.match.MatchType;

public class RegexFilter {
    public static final String MATCH_ANY_LETTER_REGEX = "[ABCDEFGHIJKLMNOPQRSTUVWXYZ]";
    private final List<String> regexList = initializeRegex();
    private final List<Character> presentLetters = new ArrayList<>();
    private final List<String> solutions = new ArrayList<>();

    public void init(List<String> solutions){
        this.solutions.addAll(solutions);
    }

    public Stream<String> getMatches() {
        String regex = String.join("", regexList);
        return solutions.stream().filter(w -> w.matches(regex) && containsAllPresentLetters(w));
    }

    private boolean containsAllPresentLetters(String currentWord) {
        return presentLetters.stream().allMatch(l -> currentWord.contains(l.toString()));
    }

    public void updateRegex(MatchResult feedback) {
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
