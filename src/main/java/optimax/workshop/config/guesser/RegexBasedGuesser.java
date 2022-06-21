package optimax.workshop.config.guesser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.Match;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.core.matcher.MatchType;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.WordMatcher;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RegexBasedGuesser implements Guesser {

    public static final String MATCH_ANY_LETTER_REGEX = "[ABCDEFGHIJKLMNOPQRSTUVWXYZ]";

    private final List<String> regexList = initializeRegex();

    private final List<Character> presentLetters = new ArrayList<>();

    private final List<String> wordList = new ArrayList<>();
    private final List<String> accepted = new ArrayList<>();

    private final Random random = new Random();
    private final WordMatcher matcher;

    public RegexBasedGuesser(WordMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public void init(Collection<Word> solutions, Collection<Word> accepted) {
        wordList.addAll(solutions.stream().map(Word::toString).collect(Collectors.toList()));
        this.accepted.addAll(accepted.stream().map(Word::toString).collect(Collectors.toList()));
    }

    @Override
    public Word nextGuess() {
        List<String> matches = getMatches().collect(Collectors.toList());
        System.out.println("Matches: "+matches);
        if (matches.size() == 1) return new Word(matches.get(0));
        List<GroupSet> groupSets = new ArrayList<>();
        for (String accepted : accepted) {
            GroupSet groupSet = new GroupSet(accepted);
            Map<MatchResult, List<String>> groups = new HashMap<>();
            for (String match : matches) {
                MatchResult result = matcher.match(new Word(match), new Word(accepted));
                groups.computeIfAbsent(result, r -> new ArrayList<>());
                groups.computeIfPresent(result, (r, w) -> {
                    w.add(match);
                    return w;
                });
            }
            groups.forEach((r, w) -> {
                groupSet.add(new Group(r, w));
            });
            groupSets.add(groupSet);
        }

        groupSets.forEach(set -> {
//            System.out.printf("%n%nWord: %s%n", set.word);
            set.groups.forEach(group  -> {
//                System.out.printf("%nGroup:%s%n%s%n", group.match, group.words);
            });
        });
//        System.out.println(matches);
        List<GroupSet> sorted = groupSets.stream().sorted(Comparator.comparing(GroupSet::size).reversed()).collect(Collectors.toList());
        return new Word(groupSets.stream().max(Comparator.comparing(GroupSet::size)).get().word);
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


    private static class GroupSet {
        private final String word;
        private final List<Group> groups = new ArrayList<>();

        public GroupSet(String word) {
            this.word = word;
        }

        public void add(Group group){
            groups.add(group);
        }

        public String getWord() {
            return word;
        }

        public List<Group> getGroups() {
            return groups;
        }

        public int size(){
            return groups.size();
        }

        @Override
        public String toString() {
            return word+" "+groups.size();
        }
    }
    private static class Group{
        private final List<String> words = new ArrayList<>();
        private final MatchResult match;


        public Group(MatchResult match) {
            this.match = match;
        }
        public Group(MatchResult match, List<String> words) {
            this.match = match;
            this.words.addAll(words);
        }


        public void add(String word){
            words.add(word);
        }

        public List<String> getWords() {
            return Collections.unmodifiableList(words);
        }

        public MatchResult getMatch() {
            return match;
        }

        public int size(){
            return words.size();
        }

        @Override
        public String toString() {
            return match.toString()+" "+words.size();
        }
    }
}
